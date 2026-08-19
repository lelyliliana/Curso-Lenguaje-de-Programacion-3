package com.lelyliliana.unidad3.ejemplo07;

/**
 * Este ejemplo utiliza la configuración de Actuator y Micrometer
 * definida para la Unidad 3.
 *
 * Las métricas se exponen en formato Prometheus mediante:
 *
 * http://localhost:8080/actuator/prometheus
 *
 * No es necesario implementar un controlador adicional porque
 * Spring Boot Actuator y Micrometer generan automáticamente
 * este endpoint.
 */
public class U3_07_Prometheus {

    private U3_07_Prometheus() {
        // Clase utilizada únicamente como guía del ejemplo.
    }
}