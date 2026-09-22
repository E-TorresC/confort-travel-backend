package com.conforttravel.api.checkin.service.impl;

import com.conforttravel.api.checkin.entity.CheckIn;
import com.conforttravel.api.checkin.entity.EstadoCheckIn;
import com.conforttravel.api.checkin.repository.CheckInRepository;
import com.conforttravel.api.checkin.service.CheckInNotificationService;
import com.conforttravel.api.notificacion.entity.Notificacion;
import com.conforttravel.api.notificacion.entity.TipoNotificacion;
import com.conforttravel.api.notificacion.repository.NotificacionRepository;
import com.conforttravel.api.usuario.entity.Usuario;
import com.conforttravel.api.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInNotificationServiceImpl
        implements CheckInNotificationService {

    private final CheckInRepository checkInRepository;
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void procesarCheckInsDisponibles() {

        OffsetDateTime ahora = OffsetDateTime.now();

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

        Usuario usuario = obtenerUsuarioNotificacion();

        for (CheckIn checkIn : checkInsDisponibles) {

            if (checkIn.getEstado() != EstadoCheckIn.PENDIENTE) {
                continue;
            }

            crearNotificacion(checkIn, usuario);

            checkIn.setEstado(EstadoCheckIn.NOTIFICADO);
            checkIn.setFechaNotificacion(ahora);

            checkInRepository.save(checkIn);

            log.info(
                    "Check-in {} notificado automáticamente. Tramo: {}",
                    checkIn.getId(),
                    checkIn.getTramo().getId()
            );
        }
    }

    private void crearNotificacion(
            CheckIn checkIn,
            Usuario usuario
    ) {

        Notificacion notificacion = Notificacion.builder()
                .tipo(TipoNotificacion.CHECK_IN_DISPONIBLE)
                .mensaje(
                        "El check-in del tramo "
                                + checkIn.getTramo().getId()
                                + " ya se encuentra disponible."
                )
                .fechaCreacion(LocalDateTime.now())
                .usuario(usuario)
                .checkIn(checkIn)
                .build();

        notificacionRepository.save(notificacion);
    }

    private Usuario obtenerUsuarioNotificacion() {

        return usuarioRepository.findFirstByActivoTrueOrderByIdAsc()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No existe un usuario activo para recibir "
                                        + "notificaciones de check-in"
                        )
                );
    }
}