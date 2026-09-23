package com.conforttravel.api.venta.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.servicio.entity.ServicioAdicional;
import com.conforttravel.api.servicio.repository.ServicioAdicionalRepository;
import com.conforttravel.api.shared.exception.BusinessException;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import com.conforttravel.api.venta.dto.VentaServicioRequest;
import com.conforttravel.api.venta.dto.VentaServicioResponse;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.venta.entity.VentaServicio;
import com.conforttravel.api.venta.entity.VentaServicioId;
import com.conforttravel.api.venta.mapper.VentaServicioMapper;
import com.conforttravel.api.venta.repository.VentaRepository;
import com.conforttravel.api.venta.repository.VentaServicioRepository;
import com.conforttravel.api.venta.service.VentaServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VentaServicioServiceImpl
        implements VentaServicioService {

    private final VentaServicioRepository ventaServicioRepository;
    private final VentaRepository ventaRepository;
    private final ServicioAdicionalRepository servicioRepository;
    private final VentaServicioMapper ventaServicioMapper;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public VentaServicioResponse agregar(
            VentaServicioRequest request
    ) {
        Venta venta = ventaRepository.findById(request.ventaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venta no encontrada con id: " + request.ventaId()
                        )
                );

        ServicioAdicional servicio =
                servicioRepository.findById(request.servicioAdicionalId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Servicio adicional no encontrado con id: "
                                                + request.servicioAdicionalId()
                                )
                        );

        if (!Boolean.TRUE.equals(servicio.getActivo())) {
            throw new BusinessException(
                    "No se puede agregar un servicio adicional inactivo"
            );
        }

        VentaServicioId id = new VentaServicioId(
                venta.getId(),
                servicio.getId()
        );

        if (ventaServicioRepository.existsById(id)) {
            throw new BusinessException(
                    "El servicio adicional ya está asociado a la venta"
            );
        }

        VentaServicio ventaServicio =
                ventaServicioMapper.toEntity(request);

        ventaServicio.setId(id);
        ventaServicio.setVenta(venta);
        ventaServicio.setServicioAdicional(servicio);

        VentaServicio guardado =
                ventaServicioRepository.save(ventaServicio);

        auditLogService.registrar(
                "AGREGAR",
                "VENTA_SERVICIO",
                venta.getId(),
                "Se agregó el servicio adicional "
                        + servicio.getId()
                        + " a la venta "
                        + venta.getId()
        );

        return ventaServicioMapper.toResponseDto(guardado);
    }

    @Override
    public VentaServicioResponse obtenerPorId(
            Long ventaId,
            Long servicioAdicionalId
    ) {
        VentaServicioId id =
                new VentaServicioId(ventaId, servicioAdicionalId);

        VentaServicio ventaServicio =
                ventaServicioRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Servicio de venta no encontrado"
                                )
                        );

        return ventaServicioMapper.toResponseDto(ventaServicio);
    }

    @Override
    public List<VentaServicioResponse> obtenerTodos() {
        return ventaServicioRepository.findAll()
                .stream()
                .map(ventaServicioMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(
            Long ventaId,
            Long servicioAdicionalId
    ) {
        VentaServicioId id =
                new VentaServicioId(
                        ventaId,
                        servicioAdicionalId
                );

        if (!ventaServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Servicio de venta no encontrado"
            );
        }

        auditLogService.registrar(
                "ELIMINAR",
                "VENTA_SERVICIO",
                ventaId,
                "Se eliminó el servicio adicional "
                        + servicioAdicionalId
                        + " de la venta "
                        + ventaId
        );

        ventaServicioRepository.deleteById(id);
    }
}