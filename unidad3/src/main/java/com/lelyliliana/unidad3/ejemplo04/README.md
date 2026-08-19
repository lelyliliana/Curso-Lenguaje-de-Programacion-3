# U3_04 - Logging con SLF4J

Este ejemplo muestra cómo registrar eventos de la aplicación utilizando diferentes niveles de log.

Los logs son uno de los pilares de la observabilidad, ya que permiten conocer qué ocurrió durante la ejecución de una aplicación.

---

## Archivo principal

`U3_04_Logging.java`

```java
package com.lelyliliana.unidad3.ejemplo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_04_Logging {

    private static final Logger logger =
            LoggerFactory.getLogger(U3_04_Logging.class);

    @GetMapping("/procesar")
    public String procesar(
            @RequestParam(defaultValue = "normal") String tipo) {

        logger.info("Se recibió una solicitud con tipo: {}", tipo);

        if ("advertencia".equalsIgnoreCase(tipo)) {
            logger.warn("La solicitud fue marcada como advertencia");
        }

        if ("error".equalsIgnoreCase(tipo)) {
            logger.error("Se simuló un error para fines académicos");
        }

        return "Solicitud procesada: " + tipo;
    }
}
```

---

## ¿Qué hace este ejemplo?

El endpoint:

```text
GET /procesar
```

recibe un parámetro llamado:

```text
tipo
```

y genera diferentes niveles de log según el valor enviado.

Por ejemplo:

```text
/procesar?tipo=normal
/procesar?tipo=advertencia
/procesar?tipo=error
```

---

## Crear el logger

La clase define:

```java
private static final Logger logger =
        LoggerFactory.getLogger(U3_04_Logging.class);
```

`LoggerFactory` crea un logger asociado a la clase:

```text
U3_04_Logging
```

Esto permite identificar desde qué parte de la aplicación se generó cada mensaje.

---

## Nivel `INFO`

La instrucción:

```java
logger.info("Se recibió una solicitud con tipo: {}", tipo);
```

registra información general sobre el funcionamiento normal de la aplicación.

Por ejemplo:

```text
INFO ... Se recibió una solicitud con tipo: normal
```

---

## Nivel `WARN`

Cuando:

```text
tipo=advertencia
```

se ejecuta:

```java
logger.warn("La solicitud fue marcada como advertencia");
```

El nivel `WARN` indica una situación que merece atención, aunque no necesariamente representa un error.

---

## Nivel `ERROR`

Cuando:

```text
tipo=error
```

se ejecuta:

```java
logger.error("Se simuló un error para fines académicos");
```

El nivel `ERROR` representa un problema importante ocurrido durante la ejecución.

En este ejemplo el error es simulado únicamente con fines de aprendizaje.

---

## Diferencia entre niveles

| Nivel | Uso general |
|---|---|
| `INFO` | Información sobre el funcionamiento normal |
| `WARN` | Situaciones que requieren atención |
| `ERROR` | Problemas importantes o fallos |

También existen otros niveles como:

```text
DEBUG
TRACE
```

que suelen utilizarse para diagnósticos más detallados.

---

## Probar un caso normal

```bash
curl "http://localhost:8080/procesar?tipo=normal"
```

Respuesta:

```text
Solicitud procesada: normal
```

En la terminal de Spring Boot aparecerá un registro de nivel:

```text
INFO
```

---

## Probar una advertencia

```bash
curl "http://localhost:8080/procesar?tipo=advertencia"
```

En la terminal aparecerán registros como:

```text
INFO
WARN
```

---

## Probar un error

```bash
curl "http://localhost:8080/procesar?tipo=error"
```

En la terminal aparecerán:

```text
INFO
ERROR
```

---

## ¿Qué información contiene un log?

Una línea de log puede contener elementos como:

```text
fecha y hora
nivel
hilo de ejecución
clase
mensaje
```

Por ejemplo:

```text
2026-08-19T14:27:20 INFO ... U3_04_Logging :
Se recibió una solicitud con tipo: advertencia
```

Esto ayuda a reconstruir qué ocurrió dentro de la aplicación.

---

## Flujo

```text
Cliente
   ↓
GET /procesar
   ↓
tipo
   ↓
Controlador
   ↓
logger
   ↓
INFO / WARN / ERROR
   ↓
terminal
```

---

## ¿Por qué utilizar logs?

Los logs permiten:

- registrar eventos relevantes;
- identificar errores;
- analizar comportamientos inesperados;
- apoyar procesos de diagnóstico;
- mantener trazabilidad de operaciones;
- comprender qué ocurrió antes de un fallo.

---

## ¿Qué debe observar el estudiante?

- Los logs son diferentes de una respuesta HTTP.
- El cliente recibe una respuesta, mientras el servidor registra información interna.
- Los niveles permiten clasificar la importancia de los eventos.
- `INFO`, `WARN` y `ERROR` tienen propósitos diferentes.
- Los logs son una fuente importante de información para observabilidad.

---

## Idea principal

```text
evento
 ↓
logger
 ↓
nivel
 ↓
registro
 ↓
diagnóstico
```