# U3_02 - Métricas con Spring Boot Actuator

Este ejemplo muestra cómo consultar métricas generadas automáticamente por Spring Boot Actuator.

Las métricas permiten medir de forma cuantitativa el comportamiento de la aplicación.

---

## Archivo principal

`U3_02_MetricasActuator.java`

```java
package com.lelyliliana.unidad3.ejemplo02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_02_MetricasActuator {

    @GetMapping("/mensaje")
    public String mensaje() {
        return "Endpoint utilizado para generar métricas";
    }
}
```

---

## ¿Qué hace este ejemplo?

El endpoint:

```text
GET /mensaje
```

se utiliza para generar tráfico dentro de la aplicación.

Cada vez que se realiza una petición, Spring Boot registra información relacionada con esa solicitud.

---

## Endpoint de métricas

Actuator expone:

```text
/actuator/metrics
```

Este endpoint muestra los nombres de las métricas disponibles.

Por ejemplo:

```text
http.server.requests
jvm.memory.used
system.cpu.usage
process.uptime
```

---

## Consultar todas las métricas disponibles

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/actuator/metrics
```

Con `curl`:

```bash
curl http://localhost:8080/actuator/metrics
```

---

## Métrica `http.server.requests`

Esta métrica registra información sobre las peticiones HTTP recibidas por la aplicación.

Puede consultarse mediante:

```bash
curl http://localhost:8080/actuator/metrics/http.server.requests
```

Una respuesta puede incluir:

```json
{
  "name": "http.server.requests",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 5
    },
    {
      "statistic": "TOTAL_TIME",
      "value": 0.34
    },
    {
      "statistic": "MAX",
      "value": 0.31
    }
  ]
}
```

---

## ¿Qué significa `COUNT`?

```text
COUNT
```

indica cuántas peticiones han sido registradas.

Por ejemplo:

```text
COUNT = 5
```

significa que se contabilizaron cinco solicitudes.

---

## ¿Qué significa `TOTAL_TIME`?

```text
TOTAL_TIME
```

representa el tiempo acumulado utilizado por las peticiones registradas.

La unidad utilizada en esta métrica es:

```text
segundos
```

---

## ¿Qué significa `MAX`?

```text
MAX
```

representa la duración máxima observada entre las solicitudes registradas.

---

## Etiquetas disponibles

La métrica también puede incluir etiquetas como:

```text
method
status
uri
outcome
exception
```

Por ejemplo:

```text
method = GET
status = 200
outcome = SUCCESS
```

Estas etiquetas permiten analizar las métricas con mayor detalle.

---

## Generar tráfico

Ejecute varias veces:

```bash
curl http://localhost:8080/mensaje
```

Por ejemplo, cinco veces.

Luego consulte:

```bash
curl http://localhost:8080/actuator/metrics/http.server.requests
```

---

## Filtrar por endpoint

También es posible consultar únicamente las peticiones asociadas a:

```text
/mensaje
```

utilizando:

```bash
curl "http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/mensaje"
```

Esto permite observar únicamente las métricas relacionadas con ese endpoint.

---

## Flujo

```text
Cliente
   ↓
GET /mensaje
   ↓
Spring Boot
   ↓
Micrometer
   ↓
métrica
http.server.requests
   ↓
Actuator
   ↓
/actuator/metrics
```

---

## ¿Qué debe observar el estudiante?

- Las métricas representan información cuantitativa.
- Actuator expone métricas automáticamente.
- `http.server.requests` registra información sobre solicitudes HTTP.
- `COUNT` indica cantidad de peticiones.
- `TOTAL_TIME` representa tiempo acumulado.
- `MAX` muestra el mayor tiempo registrado.
- Las etiquetas permiten filtrar y clasificar información.

---

## Idea principal

```text
peticiones
   ↓
métricas
   ↓
Actuator
   ↓
información cuantificable
```