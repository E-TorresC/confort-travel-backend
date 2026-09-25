package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.notificacion.repository.NotificacionRepository;
import com.conforttravel.api.tramo.entity.Tramo;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.venta.entity.Venta;
import com.conforttravel.api.viaje.entity.Viaje;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInNotificationServiceImplTest {

    @Mock
    private CheckInRepository checkInRepository;
    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private CheckInNotificationServiceImpl checkInNotificationService;

    @Test
    void procesarCheckInsDisponibles_SinPendientes_NoHaceNada() {
        // Arrange
        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(
                eq(EstadoCheckIn.PENDIENTE), any(OffsetDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act
        checkInNotificationService.procesarCheckInsDisponibles();

        // Assert
        verify(notificacionRepository, never()).save(any());
        verify(checkInRepository, never()).save(any());
    }

    @Test
    void procesarCheckInsDisponibles_ConPendientes_NotificaAlAgenteResponsableYActualizaEstado() {
        // Arrange
        Usuario agente = new Usuario();
        agente.setId(42L);

        Venta venta = new Venta();
        venta.setAgente(agente);

        Viaje viaje = new Viaje();
        viaje.setVenta(venta);

        Tramo tramo = new Tramo();
        tramo.setId(100L);
        tramo.setViaje(viaje);

        CheckIn checkInPendiente = new CheckIn();
        checkInPendiente.setId(1L);
        checkInPendiente.setEstado(EstadoCheckIn.PENDIENTE);
        checkInPendiente.setTramo(tramo);
        checkInPendiente.setFechaDisponible(OffsetDateTime.now().minusHours(1));

        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(
                eq(EstadoCheckIn.PENDIENTE), any(OffsetDateTime.class)))
                .thenReturn(List.of(checkInPendiente));

        // Act
        checkInNotificationService.procesarCheckInsDisponibles();

        // Assert
        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionRepository, times(1)).save(captor.capture());

        Notificacion notificacion = captor.getValue();
        assertEquals(agente, notificacion.getUsuario());
        assertEquals(checkInPendiente, notificacion.getCheckIn());

        assertEquals(EstadoCheckIn.NOTIFICADO, checkInPendiente.getEstado());
        assertNotNull(checkInPendiente.getFechaNotificacion());
        verify(checkInRepository, times(1)).save(checkInPendiente);
    }

    @Test
    void procesarCheckInsDisponibles_CadaCheckInNotificaASuPropioAgente() {
        // Arrange: dos check-ins con agentes distintos
        Usuario agenteA = new Usuario(); agenteA.setId(1L);
        Usuario agenteB = new Usuario(); agenteB.setId(2L);

        CheckIn checkInA = construirCheckIn(10L, 100L, agenteA);
        CheckIn checkInB = construirCheckIn(20L, 200L, agenteB);

        when(checkInRepository.findByEstadoAndFechaDisponibleLessThanEqual(
                eq(EstadoCheckIn.PENDIENTE), any(OffsetDateTime.class)))
                .thenReturn(List.of(checkInA, checkInB));

        // Act
        checkInNotificationService.procesarCheckInsDisponibles();

        // Assert
        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionRepository, times(2)).save(captor.capture());

        List<Notificacion> guardadas = captor.getAllValues();
        assertEquals(agenteA, guardadas.get(0).getUsuario());
        assertEquals(agenteB, guardadas.get(1).getUsuario());

        verify(checkInRepository, times(2)).save(any(CheckIn.class));
    }

    private CheckIn construirCheckIn(Long checkInId, Long tramoId, Usuario agente) {
        Venta venta = new Venta();
        venta.setAgente(agente);

        Viaje viaje = new Viaje();
        viaje.setVenta(venta);

        Tramo tramo = new Tramo();
        tramo.setId(tramoId);
        tramo.setViaje(viaje);

        CheckIn checkIn = new CheckIn();
        checkIn.setId(checkInId);
        checkIn.setEstado(EstadoCheckIn.PENDIENTE);
        checkIn.setTramo(tramo);
        checkIn.setFechaDisponible(OffsetDateTime.now().minusHours(1));
        return checkIn;
    }
}