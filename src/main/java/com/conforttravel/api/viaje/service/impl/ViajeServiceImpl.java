package com.conforttravel.api.viaje.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.shared.exception.ConflictException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.repository.VentaRepository;
import com.conforttravel.api.viaje.dto.ViajeRequest;
import com.conforttravel.api.viaje.dto.ViajeResponse;
import com.conforttravel.api.viaje.entity.Viaje;
import com.conforttravel.api.viaje.mapper.ViajeMapper;
import com.conforttravel.api.viaje.repository.ViajeRepository;
import com.conforttravel.api.viaje.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViajeServiceImpl implements ViajeService {

    private final ViajeRepository viajeRepository;
    private final VentaRepository ventaRepository;
    private final ViajeMapper viajeMapper;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public ViajeResponse crear(ViajeRequest request) {

        Venta venta = ventaRepository.findById(request.ventaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venta no encontrada con id: "
                                        + request.ventaId()
                        )
                );

        if (viajeRepository.existsByVentaId(request.ventaId())) {
            throw new ConflictException(
                    "La venta con id "
                            + request.ventaId()
                            + " ya tiene un viaje asociado"
            );
        }

        Viaje viaje = viajeMapper.toEntity(request);
        viaje.setVenta(venta);

        Viaje guardado = viajeRepository.save(viaje);

        auditLogService.registrar(
                "CREAR",
                "VIAJE",
                guardado.getId(),
                "Se creó el viaje asociado a la venta: "
                        + guardado.getVenta().getId()
        );

        return viajeMapper.toResponseDto(guardado);
    }

    @Override
    public ViajeResponse obtenerPorId(Long id) {
        return viajeMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<ViajeResponse> obtenerTodos() {
        return viajeRepository.findAll()
                .stream()
                .map(viajeMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Viaje viaje = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "VIAJE",
                viaje.getId(),
                "Se eliminó el viaje asociado a la venta: "
                        + viaje.getVenta().getId()
        );

        viajeRepository.delete(viaje);
    }

    private Viaje obtenerEntidad(Long id) {
        return viajeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Viaje no encontrado con id: " + id
                        )
                );
    }
}