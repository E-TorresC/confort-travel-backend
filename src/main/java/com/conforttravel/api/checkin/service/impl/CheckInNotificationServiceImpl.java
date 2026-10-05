package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.checkin.service.CheckInNotificationService;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.notificacion.entity.TipoNotificacion;
import com.conforttravel.api.notificacion.repository.NotificacionRepository;
import com.conforttravel.api.usuario.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInNotificationServiceImpl
        implements CheckInNotificationService {

    private final CheckInRepository checkInRepository;
    private final NotificacionRepository notificacionRepository;

    @Override
    @Transactional
    public void procesarCheckInsDisponibles() {

        OffsetDateTime ahora = OffsetDateTime.now(ZoneOffset.UTC);

        List<CheckIn> checkInsDisponibles =
                checkInRepository
                        .findByEstadoAndFechaDisponibleLessThanEqual(
                                EstadoCheckIn.PENDIENTE,
                                ahora
                        );

        if (checkInsDisponibles.isEmpty()) {
            log.debug(
                    "Scheduler de check-in: no existen check-ins pendientes disponibles"
            );
            return;
        }

        for (CheckIn checkIn : checkInsDisponibles) {

            if (checkIn.getEstado() != EstadoCheckIn.PENDIENTE) {
                continue;
            }

            crearNotificacion(checkIn);

            checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
            checkIn.setFechaNotificacion(ahora);

            checkInRepository.save(checkIn);

            log.info(
                    "Check-in {} notificado automáticamente al agente responsable. Cliente: {} {}",
                    checkIn.getId(),
                    checkIn.getTramo().getVenta().getCliente().getNombres(),
                    checkIn.getTramo().getVenta().getCliente().getApellidos()
            );
        }
    }

    private void crearNotificacion(CheckIn checkIn) {

        Usuario agenteResponsable = checkIn.getTramo().getVenta().getAgente();
        String nombresCliente = checkIn.getTramo().getVenta().getCliente().getNombres();
        String apellidosCliente = checkIn.getTramo().getVenta().getCliente().getApellidos();

        Notificacion notificacion = Notificacion.builder()
                .tipo(TipoNotificacion.CHECK_IN_DISPONIBLE)
                .mensaje(
                        "El check-in del cliente "
                                + nombresCliente
                                + " "
                                + apellidosCliente
                                + " ya se encuentra disponible."
                )
                .fechaCreacion(OffsetDateTime.now(ZoneOffset.UTC))
                .usuario(agenteResponsable)
                .checkIn(checkIn)
                .build();

        notificacionRepository.save(notificacion);
    }
}