package com.conforttravel.api.scheduler;

import com.conforttravel.api.checkin.service.CheckInNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckInScheduler {

    private final CheckInNotificationService checkInNotificationService;

    @Scheduled(
            fixedDelayString = "${confort.travel.scheduler.check-in.fixed-delay-ms:60000}"
    )
    public void procesarCheckIns() {

        log.debug("Iniciando Scheduler de Check-in");

        try {

            checkInNotificationService.procesarCheckInsDisponibles();

        } catch (Exception exception) {

            log.error(
                    "Error durante la ejecución del Scheduler de Check-in",
                    exception
            );
        }

        log.debug("Finalizando Scheduler de Check-in");
    }
}