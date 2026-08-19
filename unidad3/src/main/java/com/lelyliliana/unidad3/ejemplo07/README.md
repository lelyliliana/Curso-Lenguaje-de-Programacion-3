# U3_07 - Exportación de métricas para Prometheus

Este ejemplo muestra cómo Spring Boot Actuator y Micrometer pueden exponer métricas en un formato compatible con Prometheus.

Prometheus es una herramienta utilizada para recolectar, almacenar y consultar métricas de aplicaciones y sistemas.

---

## Archivo principal

`U3_07_Prometheus.java`

```java
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
```

---

## ¿Qué hace este ejemplo?

A diferencia de los ejemplos anteriores, aquí no se crea un endpoint manual.

Spring Boot Actuator y Micrometer exponen automáticamente:

```text
/actuator/prometheus
```

Este endpoint devuelve las métricas en un formato que Prometheus puede interpretar.

---

## Dependencia utilizada

En `unidad3/pom.xml` se utiliza:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

Esta dependencia permite que Micrometer exporte las métricas en formato Prometheus.

---

## Configuración

En:

```text
src/main/resources/application.properties
```

se encuentra:

```properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

La palabra:

```text
prometheus
```

habilita el endpoint correspondiente.

---

## Consultar las métricas

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/actuator/prometheus
```

Con `curl`:

```bash
curl http://localhost:8080/actuator/prometheus
```

---

## ¿Cómo se ve la respuesta?

La respuesta no tiene formato JSON.

Prometheus utiliza un formato de texto especializado.

Por ejemplo:

```text
# HELP process_cpu_usage The "recent cpu usage" for the Java Virtual Machine process
# TYPE process_cpu_usage gauge
process_cpu_usage 0.02
```

También pueden aparecer métricas como:

```text
system_cpu_usage
process_uptime_seconds
jvm_memory_used_bytes
http_server_requests_seconds_count
logback_events_total
```

---

## `HELP`

Las líneas:

```text
# HELP
```

describen qué representa una métrica.

Por ejemplo:

```text
# HELP process_cpu_usage ...
```

explica el significado de:

```text
process_cpu_usage
```

---

## `TYPE`

Las líneas:

```text
# TYPE
```

indican el tipo de métrica.

Por ejemplo:

```text
counter
gauge
```

---

## Counter

Un `counter` representa un valor acumulativo.

Por ejemplo:

```text
logback_events_total
```

puede indicar la cantidad de eventos de log registrados.

---

## Gauge

Un `gauge` representa un valor que puede subir o bajar.

Por ejemplo:

```text
system_cpu_usage
```

puede cambiar según el uso actual del procesador.

---

## Ejemplos de métricas observadas

La salida puede incluir información como:

```text
logback_events_total
process_cpu_time_ns_total
process_cpu_usage
process_files_open_files
process_uptime_seconds
system_cpu_count
system_cpu_usage
system_load_average_1m
tomcat_sessions_active_current_sessions
```

Estas métricas describen diferentes aspectos del sistema y del proceso Java.

---

## Relación con las métricas personalizadas

La métrica creada anteriormente:

```text
curso.consultas
```

también puede exportarse mediante Prometheus.

El nombre puede adaptarse al formato utilizado por Prometheus.

Por ejemplo:

```text
curso_consultas_total
```

---

## Flujo

```text
Aplicación
   ↓
Micrometer
   ↓
métricas
   ↓
Prometheus Registry
   ↓
/actuator/prometheus
   ↓
Prometheus
```

---

## ¿Es necesario instalar Prometheus?

No para comprender este ejemplo.

El objetivo inicial es observar que la aplicación ya puede exponer métricas en un formato compatible con Prometheus.

Una instalación real de Prometheus podría configurarse posteriormente para consultar periódicamente este endpoint.

---

## ¿Qué debe observar el estudiante?

- Micrometer permite exportar métricas en distintos formatos.
- Prometheus utiliza un formato de texto específico.
- `/actuator/prometheus` es generado automáticamente.
- `HELP` describe una métrica.
- `TYPE` indica su tipo.
- Las métricas pueden representar CPU, memoria, peticiones, logs y otros aspectos.
- Una aplicación observable puede integrarse con herramientas externas de monitoreo.

---

## Idea principal

```text
Aplicación
   ↓
Micrometer
   ↓
métricas
   ↓
/actuator/prometheus
   ↓
herramienta externa
de monitoreo
```