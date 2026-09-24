package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.auditoria.service.AuditLogService;
import com.conforttravel.api.checkin.dto.CheckInResponse;
import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.mapper.CheckInMapper;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.shared.exception.ConflictException;
import com.conforttravel.api.tramo.repository.TramoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInServiceImplTest {

    @Mock
    private CheckInRepository checkInRepository;
    @Mock
    private TramoRepository tramoRepository;
    @Mock
    private CheckInMapper checkInMapper;
    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private CheckInServiceImpl checkInService;

    private CheckIn checkIn;

    @BeforeEach
    void setUp() {
        checkIn = CheckIn.builder()
                .id(1L)
                .estado(EstadoCheckIn.PENDIENTE)
                .fechaDisponible(OffsetDateTime.now())
                .build();
    }

    @Test
    void marcarNotificado_CuandoEstadoEsPendiente_Exito() {
        // Arrange
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(checkIn);
        when(checkInMapper.toResponseDto(any(CheckIn.class))).thenReturn(
                new CheckInResponse(1L, EstadoCheckIn.NOTIFICADO, null, OffsetDateTime.now(), null, 1L)
        );

        // Act
        CheckInResponse response = checkInService.marcarNotificado(1L);

        // Assert
        assertEquals(EstadoCheckIn.NOTIFICADO, checkIn.getEstado());
        assertNotNull(checkIn.getFechaNotificacion());
        verify(auditLogService, times(1)).registrar(eq("NOTIFICAR"), eq("CHECK_IN"), eq(1L), anyString());
    }

    @Test
    void marcarNotificado_CuandoEstadoNoEsPendiente_LanzaConflictException() {
        // Arrange
        checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> checkInService.marcarNotificado(1L));
        assertEquals("El check-in no se encuentra en estado PENDIENTE", exception.getMessage());
        verify(checkInRepository, never()).save(any());
    }

    @Test
    void marcarAtendido_CuandoEstadoEsNotificado_Exito() {
        // Arrange
        checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(checkIn);
        when(checkInMapper.toResponseDto(any(CheckIn.class))).thenReturn(
                new CheckInResponse(1L, EstadoCheckIn.ATENDIDO, null, null, OffsetDateTime.now(), 1L)
        );

        // Act
        CheckInResponse response = checkInService.marcarAtendido(1L);

        // Assert
        assertEquals(EstadoCheckIn.ATENDIDO, checkIn.getEstado());
        assertNotNull(checkIn.getFechaAtencion());
        verify(auditLogService, times(1)).registrar(eq("ATENDER"), eq("CHECK_IN"), eq(1L), anyString());
    }

    @Test
    void marcarAtendido_CuandoEstadoEsPendiente_LanzaConflictException() {
        // Arrange
        checkIn.setEstado(EstadoCheckIn.PENDIENTE); // Intento de salto directo
        when(checkInRepository.findById(1L)).thenReturn(Optional.of(checkIn));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, () -> checkInService.marcarAtendido(1L));
        assertEquals("El check-in debe estar NOTIFICADO antes de marcarlo como ATENDIDO", exception.getMessage());
        verify(checkInRepository, never()).save(any());
    }
}