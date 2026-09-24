package com.conforttravel.api.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ConfortTravelHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Aquí en el futuro se implementará la lógica real (ping) a las APIs de aerolíneas o AGIL.
        // Por ahora, simulamos que la conexión a los proveedores de vuelos está activa.
        boolean externalServicesUp = true;

        if (externalServicesUp) {
            return Health.up()
                    .withDetail("Integracion_AGIL", "Disponible")
                    .withDetail("Integracion_LATAM", "Disponible")
                    .withDetail("Estado", "Todos los sistemas de emisión operando con normalidad")
                    .build();
        } else {
            return Health.down()
                    .withDetail("Integracion_AGIL", "Timeout / No responde")
                    .withDetail("Estado", "Problemas de comunicación con los proveedores de vuelos")
                    .build();
        }
    }
}