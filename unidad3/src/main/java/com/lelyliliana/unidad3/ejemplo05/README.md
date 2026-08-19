# U3_05 - Health Indicator personalizado

Este ejemplo muestra cómo agregar un indicador de salud propio a Spring Boot Actuator.

Actuator ya proporciona información sobre componentes como disco, SSL o conectividad básica. Sin embargo, una aplicación también puede necesitar reportar el estado de componentes propios.

---

## Archivo principal

`U3_05_HealthIndicatorPersonalizado.java`

```java
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
                    .withDetail(
                            "descripcion",
                            "Componente académico funcionando correctamente")
                    .build();
        }

        return Health.down()
                .withDetail("servicio", "No disponible")
                .build();
    }
}
```

---

## ¿Qué hace este ejemplo?

La clase agrega un componente propio al endpoint:

```text
/actuator/health
```

Además del estado general de Spring Boot, aparecerá información relacionada con:

```text
U3_05_HealthIndicatorPersonalizado
```

---

## Interfaz `HealthIndicator`

La clase implementa:

```java
HealthIndicator
```

Esta interfaz permite definir cómo se determina el estado de salud de un componente.

Para ello se implementa el método:

```java
public Health health()
```

---

## Anotación `@Component`

La anotación:

```java
@Component
```

permite que Spring detecte automáticamente esta clase y la registre dentro del contexto de la aplicación.

Actuator puede encontrar entonces el `HealthIndicator` y añadirlo al endpoint de salud.

---

## Estado `UP`

Cuando el componente se encuentra disponible se utiliza:

```java
Health.up()
```

Esto representa:

```text
status = UP
```

También se pueden agregar detalles:

```java
.withDetail("servicio", "Disponible")
```

y:

```java
.withDetail(
    "descripcion",
    "Componente académico funcionando correctamente"
)
```

---

## Estado `DOWN`

Si el componente no estuviera disponible podría utilizarse:

```java
Health.down()
```

Esto representa:

```text
status = DOWN
```

En este ejemplo el valor:

```java
boolean servicioDisponible = true;
```

se mantiene fijo únicamente con fines académicos.

En una aplicación real, esa condición podría depender de:

- disponibilidad de una base de datos;
- acceso a un servicio externo;
- conexión con un dispositivo;
- existencia de un recurso;
- estado de otro componente.

---

## Probar el indicador

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/actuator/health
```

Con `curl`:

```bash
curl http://localhost:8080/actuator/health
```

---

## Respuesta esperada

Además de otros componentes de Actuator, debe aparecer información similar a:

```json
{
  "status": "UP",
  "components": {
    "u3_05_HealthIndicatorPersonalizado": {
      "status": "UP",
      "details": {
        "servicio": "Disponible",
        "descripcion": "Componente académico funcionando correctamente"
      }
    }
  }
}
```

---

## Flujo

```text
Aplicación
    ↓
HealthIndicator personalizado
    ↓
health()
    ↓
UP / DOWN
    ↓
Actuator
    ↓
/actuator/health
```

---

## ¿Por qué crear indicadores personalizados?

Permiten observar el estado de componentes que Spring Boot no conoce automáticamente.

Por ejemplo:

```text
API externa
base de datos
servicio interno
sensor
cola de mensajes
almacenamiento
```

Esto hace que el endpoint de salud represente mejor el estado real de la aplicación.

---

## ¿Qué debe observar el estudiante?

- `HealthIndicator` permite agregar comprobaciones propias.
- `Health.up()` indica funcionamiento correcto.
- `Health.down()` representa indisponibilidad.
- Los detalles permiten proporcionar información adicional.
- Actuator integra automáticamente los indicadores registrados como componentes.
- El estado general de una aplicación puede depender de varios componentes.

---

## Idea principal

```text
componente propio
      ↓
HealthIndicator
      ↓
UP / DOWN
      ↓
Actuator
      ↓
estado observable
```