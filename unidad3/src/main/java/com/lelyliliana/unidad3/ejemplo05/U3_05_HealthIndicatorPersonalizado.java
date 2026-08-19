package com.lelyliliana.unidad3.ejemplo05;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class U3_05_HealthIndicatorPersonalizado
        implements HealthIndicator {

    @Override
    public Health health() {

        boolean servicioDisponible = true;

        if (servicioDisponible) {
            return Health.up()
                    .withDetail("servicio", "Disponible")
                    .withDetail("descripcion",
                            "Componente académico funcionando correctamente")
                    .build();
        }

        return Health.down()
                .withDetail("servicio", "No disponible")
                .build();
    }
}