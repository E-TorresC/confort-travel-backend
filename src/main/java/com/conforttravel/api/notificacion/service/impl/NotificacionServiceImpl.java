package com.conforttravel.api.notificacion.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.notificacion.dto.NotificacionResponse;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.notificacion.mapper.NotificacionMapper;
import com.conforttravel.api.notificacion.repository.NotificacionRepository;
import com.conforttravel.api.notificacion.service.NotificacionService;
import com.conforttravel.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;
    private final AuditLogService auditLogService;

    @Override
    public List<NotificacionResponse> obtenerTodas() {

        return notificacionRepository.findAll()
                .stream()
                .map(notificacionMapper::toResponseDto)
                .toList();
    }

    @Override
    public NotificacionResponse obtenerPorId(Long id) {

        return notificacionMapper.toResponseDto(
                obtenerEntidad(id)
        );
    }

    @Override
    public List<NotificacionResponse> obtenerNoLeidas() {

        return notificacionRepository
                .findByFechaLecturaIsNullOrderByFechaCreacionDesc()
                .stream()
                .map(notificacionMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public NotificacionResponse marcarComoLeida(Long id) {

        Notificacion notificacion = obtenerEntidad(id);

        if (notificacion.getFechaLectura() == null) {

            notificacion.setFechaLectura(
                    OffsetDateTime.now(ZoneOffset.UTC)
            );

            notificacion = notificacionRepository.save(notificacion);

            auditLogService.registrar(
                    "LEER",
                    "NOTIFICACION",
                    notificacion.getId(),
                    "La notificación fue marcada como leída"
            );
        }

        return notificacionMapper.toResponseDto(notificacion);
    }

    private Notificacion obtenerEntidad(Long id) {

        return notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notificación no encontrada con id: " + id
                        )
                );
    }
}