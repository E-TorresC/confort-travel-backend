package com.conforttravel.api.config;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ConfortTravelHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // TODO: Implementar ping real (HTTP/REST) a los proveedores cuando se tengan las credenciales/URLs.
        // Actualmente se marca claramente como MOCK (simulado) por requerimientos de observabilidad.
        boolean isDevSimulationOk = true;

        if (isDevSimulationOk) {
            return Health.up()
                    .withDetail("Integracion_AGIL", "MOCK - Disponible")
                    .withDetail("Integracion_LATAM", "MOCK - Disponible")
                    .withDetail("Estado", "MOCK - Todos los sistemas operando con normalidad (Simulado)")
                    .build();
        } else {
            return Health.down()
                    .withDetail("Integracion_AGIL", "MOCK - Timeout / No responde")
                    .withDetail("Integracion_LATAM", "MOCK - Timeout / No responde")
                    .withDetail("Estado", "MOCK - Problemas de comunicación (Simulado)")
                    .build();
        }
    }
}