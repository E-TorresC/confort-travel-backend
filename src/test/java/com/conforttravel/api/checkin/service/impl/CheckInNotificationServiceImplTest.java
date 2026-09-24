package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.notificacion.repository.NotificacionRepository;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInNotificationServiceImplTest {

    @Mock
    private CheckInRepository checkInRepository;
    @Mock
    private NotificacionRepository notificacionRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CheckInNotificationServiceImpl checkInNotificationService;

    @Test
    void procesarCheckInsDisponibles_SinPendientes_NoHaceNada() {
        // Arrange
        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(eq(EstadoCheckIn.PENDIENTE), any(OffsetDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        checkInNotificationService.procesarCheckInsDisponibles();

        // Assert
        verify(usuarioRepository, never()).findFirstByActivoTrueOrderByIdAsc();
        verify(notificacionRepository, never()).save(any());
        verify(checkInRepository, never()).save(any());
    }

    @Test
    void procesarCheckInsDisponibles_ConPendientes_NotificaYActualizaEstado() {
        // Arrange
        Tramo tramo = new Tramo();
        tramo.setId(100L);

        CheckIn checkInPendiente = new CheckIn();
        checkInPendiente.setId(1L);
        checkInPendiente.setEstado(EstadoCheckIn.PENDIENTE);
        checkInPendiente.setTramo(tramo);
        checkInPendiente.setFechaDisponible(OffsetDateTime.now().minusHours(1));

        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setId(1L);

        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(eq(EstadoCheckIn.PENDIENTE), any(OffsetDateTime.class)))
                .thenReturn(List.of(checkInPendiente));
        when(usuarioRepository.findFirstByActivoTrueOrderByIdAsc()).thenReturn(Optional.of(usuarioAdmin));

        // Act
        checkInNotificationService.procesarCheckInsDisponibles();

        // Assert
        // Verifica que se haya guardado una notificación
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));

        // Verifica que el checkIn haya cambiado de estado y se haya persistido
        assertEquals(EstadoCheckIn.NOTIFICADO, checkInPendiente.getEstado());
        assertNotNull(checkInPendiente.getFechaNotificacion());
        verify(checkInRepository, times(1)).save(checkInPendiente);
    }

    @Test
    void procesarCheckInsDisponibles_FallaSiNoHayUsuarioActivoParaNotificar() {
        // Arrange
        CheckIn checkInPendiente = new CheckIn();
        checkInPendiente.setEstado(EstadoCheckIn.PENDIENTE);

        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(any(), any()))
                .thenReturn(List.of(checkInPendiente));

        when(usuarioRepository.findFirstByActivoTrueOrderByIdAsc()).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                checkInNotificationService.procesarCheckInsDisponibles()
        );
        assertEquals("No existe un usuario activo para recibir notificaciones de check-in", exception.getMessage());

        verify(notificacionRepository, never()).save(any());
    }
}