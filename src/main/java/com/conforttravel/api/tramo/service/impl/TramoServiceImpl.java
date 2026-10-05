package com.conforttravel.api.tramo.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.checkin.dto.CheckInRequest;
import com.conforttravel.api.checkin.service.CheckInService;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.shared.util.GeneradorCodigoUtil;
import com.conforttravel.api.tramo.dto.TramoRequest;
import com.conforttravel.api.tramo.dto.TramoResponse;
import com.conforttravel.api.tramo.entity.TipoTramo;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.tramo.mapper.TramoMapper;
import com.conforttravel.api.tramo.repository.TramoRepository;
import com.conforttravel.api.tramo.service.TramoService;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TramoServiceImpl implements TramoService {

    private final TramoRepository tramoRepository;
    private final VentaRepository ventaRepository;
    private final TramoMapper tramoMapper;
    private final AuditLogService auditLogService;
    private final CheckInService checkInService;
    private final GeneradorCodigoUtil generadorCodigoUtil;

    @Override
    @Transactional
    public TramoResponse crear(TramoRequest request) {
        validarFechas(request);
        validarFechasIdaRetorno(request, null);

        Venta venta = obtenerVenta(request.ventaId());

        Tramo tramo = tramoMapper.toEntity(request);
        tramo.setVenta(venta);

        String ultimoCodigo = tramoRepository.findTopByOrderByIdDesc()
                .map(Tramo::getCodigo)
                .orElse(null);
        tramo.setCodigo(generadorCodigoUtil.generarSiguiente("TRM", ultimoCodigo));

        Tramo guardado = tramoRepository.save(tramo);

        auditLogService.registrar(
                "CREAR",
                "TRAMO",
                guardado.getId(),
                "Se creó el tramo desde " + guardado.getOrigen() + " hacia " + guardado.getDestino()
        );

        checkInService.crear(new CheckInRequest(guardado.getId()));
        return tramoMapper.toResponseDto(guardado);
    }

    @Override
    public TramoResponse obtenerPorId(Long id) {
        return tramoMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<TramoResponse> obtenerTodos() {
        return tramoRepository.findAll().stream().map(tramoMapper::toResponseDto).toList();
    }

    @Override
    public List<TramoResponse> obtenerPorVenta(Long ventaId) {
        return tramoRepository.findByVentaId(ventaId).stream().map(tramoMapper::toResponseDto).toList();
    }

    @Override
    @Transactional
    public TramoResponse actualizar(Long id, TramoRequest request) {
        validarFechas(request);
        validarFechasIdaRetorno(request, id);

        Tramo tramo = obtenerEntidad(id);
        Venta venta = obtenerVenta(request.ventaId());

        tramo.setTipoTramo(request.tipoTramo());
        tramo.setTipoViaje(request.tipoViaje());
        tramo.setOrigen(request.origen());
        tramo.setDestino(request.destino());
        tramo.setFechaHoraSalida(request.fechaHoraSalida());
        tramo.setFechaHoraLlegada(request.fechaHoraLlegada());
        tramo.setVenta(venta);

        Tramo actualizado = tramoRepository.save(tramo);

        auditLogService.registrar(
                "ACTUALIZAR",
                "TRAMO",
                actualizado.getId(),
                "Se actualizó el tramo desde " + actualizado.getOrigen() + " hacia " + actualizado.getDestino()
        );

        return tramoMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tramo tramo = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "TRAMO",
                tramo.getId(),
                "Se eliminó el tramo desde " + tramo.getOrigen() + " hacia " + tramo.getDestino()
        );

        tramoRepository.delete(tramo);
    }

    private Tramo obtenerEntidad(Long id) {
        return tramoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tramo no encontrado con id: " + id));
    }

    private Venta obtenerVenta(Long id) {
        return ventaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
    }

    private void validarFechas(TramoRequest request) {
        if (request.fechaHoraLlegada() != null && !request.fechaHoraLlegada().isAfter(request.fechaHoraSalida())) {
            throw new BusinessException("La fecha/hora de llegada debe ser posterior a la fecha/hora de salida");
        }
    }

    private void validarFechasIdaRetorno(TramoRequest request, Long tramoId) {
        List<Tramo> tramosVenta = tramoRepository.findByVentaId(request.ventaId());

        if (request.tipoTramo() == TipoTramo.RETORNO) {
            tramosVenta.stream()
                    .filter(t -> t.getTipoTramo() == TipoTramo.IDA && !t.getId().equals(tramoId))
                    .findFirst()
                    .ifPresent(ida -> {
                        if (request.fechaHoraSalida().isBefore(ida.getFechaHoraSalida())) {
                            throw new BusinessException("La fecha de salida del tramo de RETORNO no puede ser anterior a la del tramo de IDA.");
                        }
                    });
        } else if (request.tipoTramo() == TipoTramo.IDA) {
            tramosVenta.stream()
                    .filter(t -> t.getTipoTramo() == TipoTramo.RETORNO && !t.getId().equals(tramoId))
                    .findFirst()
                    .ifPresent(retorno -> {
                        if (request.fechaHoraSalida().isAfter(retorno.getFechaHoraSalida())) {
                            throw new BusinessException("La fecha de salida del tramo de IDA no puede ser posterior a la del tramo de RETORNO.");
                        }
                    });
        }
    }
}