# U3_01 - Observabilidad básica con Spring Boot Actuator

Este ejemplo introduce Spring Boot Actuator como herramienta para observar el estado interno de una aplicación.

---

## Archivo principal

`U3_01_ActuatorBasico.java`

```java
package com.lelyliliana.unidad3.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad3")
public class U3_01_ActuatorBasico {

    public static void main(String[] args) {
        SpringApplication.run(U3_01_ActuatorBasico.class, args);
    }
}
```

---

## ¿Qué hace esta clase?

Esta es la clase principal de la Unidad 3.

La anotación:

```java
@SpringBootApplication
```

permite iniciar Spring Boot.

Como los ejemplos están distribuidos en diferentes paquetes, se utiliza:

```java
scanBasePackages = "com.lelyliliana.unidad3"
```

para que Spring detecte todos los componentes de la unidad.

---

## Configuración de Actuator

La configuración se encuentra en:

```text
src/main/resources/application.properties
```

y contiene:

```properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=always
```

Esto expone los endpoints:

```text
/actuator/health
/actuator/info
/actuator/metrics
/actuator/prometheus
```

---

## Endpoint `health`

El endpoint:

```text
/actuator/health
```

permite conocer el estado general de la aplicación.

Puede devolver:

```json
{
  "status": "UP"
}
```

El valor:

```text
UP
```

indica que la aplicación se encuentra operativa.

---

## Información detallada

Como se configuró:

```properties
management.endpoint.health.show-details=always
```

también pueden aparecer componentes como:

```text
diskSpace
ping
ssl
```

Por ejemplo:

```json
{
  "status": "UP",
  "components": {
    "diskSpace": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

---

## Ejecutar la Unidad 3

Desde la raíz del repositorio:

```bash
mvn -pl unidad3 spring-boot:run
```

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/actuator/health
```

---

## Probar con `curl`

```bash
curl http://localhost:8080/actuator/health
```

---

## ¿Qué es observabilidad?

La observabilidad permite obtener información sobre el estado interno de una aplicación a partir de sus salidas.

En este primer ejemplo:

```text
Aplicación
   ↓
Actuator
   ↓
/actuator/health
   ↓
estado interno
```

---

## ¿Qué debe observar el estudiante?

- Actuator permite exponer información operativa de la aplicación.
- `health` indica si la aplicación está funcionando.
- Los componentes internos pueden reportar su propio estado.
- La observabilidad comienza por poder consultar información sobre el sistema.

---

## Idea principal

```text
Aplicación
   ↓
Actuator
   ↓
health
   ↓
estado
```