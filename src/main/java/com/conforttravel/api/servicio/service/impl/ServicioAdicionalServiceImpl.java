package com.conforttravel.api.servicio.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.servicio.dto.ServicioAdicionalFilterRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalRequest;
import com.conforttravel.api.servicio.dto.ServicioAdicionalResponse;
import com.conforttravel.api.servicio.entity.ServicioAdicional;
import com.conforttravel.api.servicio.mapper.ServicioAdicionalMapper;
import com.conforttravel.api.servicio.repository.ServicioAdicionalRepository;
import com.conforttravel.api.servicio.service.ServicioAdicionalService;
import com.conforttravel.api.servicio.specification.ServicioAdicionalSpecificationBuilder;
import com.conforttravel.api.shared.dto.PageResponse;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioAdicionalServiceImpl
        implements ServicioAdicionalService {

    private final ServicioAdicionalRepository servicioRepository;
    private final ServicioAdicionalMapper servicioMapper;
    private final AuditLogService auditLogService;
    private final ServicioAdicionalSpecificationBuilder specificationBuilder;

    @Override
    @Transactional
    public ServicioAdicionalResponse crear(
            ServicioAdicionalRequest request
    ) {
        ServicioAdicional servicio = servicioMapper.toEntity(request);

        if (servicio.getActivo() == null) {
            servicio.setActivo(true);
        }

        ServicioAdicional guardado = servicioRepository.save(servicio);

        auditLogService.registrar(
                "CREAR",
                "SERVICIO_ADICIONAL",
                guardado.getId(),
                "Se creó el servicio adicional: " + guardado.getNombre()
        );

        return servicioMapper.toResponseDto(guardado);
    }

    @Override
    public ServicioAdicionalResponse obtenerPorId(Long id) {
        return servicioMapper.toResponseDto(obtenerEntidad(id));
    }

    @Override
    public List<ServicioAdicionalResponse> obtenerTodos() {
        return servicioRepository.findAll()
                .stream()
                .map(servicioMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ServicioAdicionalResponse actualizar(
            Long id,
            ServicioAdicionalRequest request
    ) {
        ServicioAdicional servicio = obtenerEntidad(id);

        servicio.setNombre(request.nombre());
        servicio.setDescripcion(request.descripcion());

        if (request.activo() != null) {
            servicio.setActivo(request.activo());
        }

        ServicioAdicional actualizado = servicioRepository.save(servicio);

        auditLogService.registrar(
                "ACTUALIZAR",
                "SERVICIO_ADICIONAL",
                actualizado.getId(),
                "Se actualizó el servicio adicional: "
                        + actualizado.getNombre()
        );

        return servicioMapper.toResponseDto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ServicioAdicional servicio = obtenerEntidad(id);

        auditLogService.registrar(
                "ELIMINAR",
                "SERVICIO_ADICIONAL",
                servicio.getId(),
                "Se eliminó el servicio adicional: "
                        + servicio.getNombre()
        );

        servicioRepository.delete(servicio);
    }

    @Override
    public PageResponse<ServicioAdicionalResponse> buscar(ServicioAdicionalFilterRequest filter, Pageable pageable) {
        return PageResponse.from(
                servicioRepository.findAll(specificationBuilder.build(filter), pageable)
                        .map(servicioMapper::toResponseDto)
        );
    }

    private ServicioAdicional obtenerEntidad(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Servicio adicional no encontrado con id: " + id
                        )
                );
    }
}