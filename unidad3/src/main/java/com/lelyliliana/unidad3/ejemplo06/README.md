# U3_06 - Trazabilidad con `traceId` y `spanId`

Este ejemplo muestra cómo identificar una solicitud mediante trazas utilizando Micrometer Tracing.

La trazabilidad permite seguir el recorrido de una solicitud a través de diferentes operaciones o componentes de una aplicación.

---

## Archivo principal

`U3_06_Trazabilidad.java`

```java
package com.lelyliliana.unidad3.ejemplo06;

import io.micrometer.tracing.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class U3_06_Trazabilidad {

    private final Tracer tracer;

    public U3_06_Trazabilidad(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping("/traza")
    public Map<String, String> consultarTraza() {

        Map<String, String> respuesta = new LinkedHashMap<>();

        if (tracer.currentSpan() != null) {
            respuesta.put(
                    "traceId",
                    tracer.currentSpan().context().traceId()
            );

            respuesta.put(
                    "spanId",
                    tracer.currentSpan().context().spanId()
            );
        }

        respuesta.put(
                "mensaje",
                "Solicitud procesada con trazabilidad"
        );

        return respuesta;
    }
}
```

---

## ¿Qué hace este ejemplo?

El endpoint:

```text
GET /traza
```

devuelve información asociada a la traza de la solicitud actual.

La respuesta puede tener esta forma:

```json
{
  "traceId": "6a8605cc467be76999c517df4c0ee1f2",
  "spanId": "99c517df4c0ee1f2",
  "mensaje": "Solicitud procesada con trazabilidad"
}
```

---

## ¿Qué es una traza?

Una traza representa el recorrido completo de una solicitud.

Puede imaginarse así:

```text
Solicitud
   ↓
Controlador
   ↓
Servicio
   ↓
Base de datos
   ↓
Respuesta
```

Todas esas operaciones pueden formar parte de una misma traza.

---

## ¿Qué es `traceId`?

`traceId` identifica la traza completa.

Por ejemplo:

```text
traceId = 6a8605cc467be76999c517df4c0ee1f2
```

Todas las operaciones relacionadas con una misma solicitud pueden compartir ese identificador.

---

## ¿Qué es `spanId`?

Un `span` representa una operación específica dentro de una traza.

Por ejemplo:

```text
Traza
├── recibir petición
├── consultar servicio
├── acceder a base de datos
└── construir respuesta
```

Cada una de esas operaciones puede tener su propio:

```text
spanId
```

---

## Relación entre `traceId` y `spanId`

Puede representarse así:

```text
traceId
   ↓
Solicitud completa
   ↓
┌────────┬────────┬────────┐
span 1   span 2   span 3
```

El `traceId` agrupa todas las operaciones.

Cada `spanId` identifica una operación particular.

---

## Inyección de `Tracer`

La clase recibe:

```java
Tracer tracer
```

mediante el constructor:

```java
public U3_06_Trazabilidad(Tracer tracer) {
    this.tracer = tracer;
}
```

Spring proporciona automáticamente la implementación configurada por Micrometer Tracing.

---

## Obtener el span actual

Se comprueba:

```java
tracer.currentSpan()
```

Si existe una traza activa, se puede acceder a su contexto.

---

## Obtener `traceId`

La instrucción:

```java
tracer.currentSpan()
        .context()
        .traceId()
```

obtiene el identificador de la traza.

---

## Obtener `spanId`

La instrucción:

```java
tracer.currentSpan()
        .context()
        .spanId()
```

obtiene el identificador del span actual.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/traza
```

---

## Probar con `curl`

```bash
curl http://localhost:8080/traza
```

Una respuesta posible es:

```json
{
  "traceId": "6a8605cc467be76999c517df4c0ee1f2",
  "spanId": "99c517df4c0ee1f2",
  "mensaje": "Solicitud procesada con trazabilidad"
}
```

Los valores cambiarán entre solicitudes.

---

## Probar nuevamente

Ejecute otra vez:

```bash
curl http://localhost:8080/traza
```

Los identificadores deben ser diferentes porque corresponden a otra solicitud.

---

## ¿Para qué sirve la trazabilidad?

La trazabilidad ayuda a:

- seguir el recorrido de una solicitud;
- identificar dónde ocurrió un problema;
- relacionar eventos de diferentes componentes;
- analizar tiempos de ejecución;
- diagnosticar aplicaciones distribuidas;
- correlacionar logs y métricas.

---

## Diferencia entre métricas, logs y trazas

```text
Métricas
→ ¿cuánto está ocurriendo?

Logs
→ ¿qué ocurrió?

Trazas
→ ¿por dónde pasó la solicitud?
```

Los tres elementos se complementan dentro de una estrategia de observabilidad.

---

## Flujo

```text
Cliente
   ↓
GET /traza
   ↓
Spring
   ↓
Micrometer Tracing
   ↓
traceId
+
spanId
   ↓
respuesta
```

---

## ¿Qué debe observar el estudiante?

- Cada solicitud puede tener una traza.
- `traceId` identifica el recorrido completo.
- `spanId` identifica una operación específica.
- `Tracer` permite acceder a la información de trazabilidad.
- Las trazas ayudan a correlacionar eventos en sistemas complejos.
- Métricas, logs y trazas cumplen funciones diferentes pero complementarias.

---

## Idea principal

```text
solicitud
   ↓
traceId
   ↓
varios spans
   ↓
seguimiento del recorrido
```