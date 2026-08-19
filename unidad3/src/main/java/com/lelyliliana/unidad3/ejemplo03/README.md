# U3_03 - Métrica personalizada con Micrometer

Este ejemplo muestra cómo crear una métrica propia utilizando Micrometer.

Hasta ahora se habían consultado métricas generadas automáticamente por Spring Boot. En este ejemplo se crea un contador personalizado.

---

## Archivo principal

`U3_03_MetricaPersonalizada.java`

```java
package com.lelyliliana.unidad3.ejemplo03;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U3_03_MetricaPersonalizada {

    private final Counter contadorConsultas;

    public U3_03_MetricaPersonalizada(MeterRegistry meterRegistry) {
        this.contadorConsultas = Counter.builder("curso.consultas")
                .description("Cantidad de consultas realizadas al endpoint")
                .register(meterRegistry);
    }

    @GetMapping("/consulta-contada")
    public String consultar() {

        contadorConsultas.increment();

        return "Consulta registrada";
    }
}
```

---

## ¿Qué hace este ejemplo?

Cada vez que un cliente consulta:

```text
GET /consulta-contada
```

la aplicación incrementa un contador.

La métrica creada se llama:

```text
curso.consultas
```

---

## `MeterRegistry`

La clase recibe:

```java
MeterRegistry meterRegistry
```

`MeterRegistry` es el registro central donde Micrometer administra las métricas de la aplicación.

Puede verse conceptualmente así:

```text
Aplicación
   ↓
MeterRegistry
   ↓
métricas registradas
```

---

## Crear un `Counter`

Se utiliza:

```java
Counter.builder("curso.consultas")
```

para crear una métrica de tipo contador.

Un `Counter` representa un valor que aumenta a medida que ocurre un evento.

Por ejemplo:

```text
1
2
3
4
5
...
```

---

## Descripción de la métrica

La instrucción:

```java
.description("Cantidad de consultas realizadas al endpoint")
```

agrega una descripción que ayuda a comprender qué representa la métrica.

---

## Registrar la métrica

La instrucción:

```java
.register(meterRegistry)
```

incorpora el contador al registro de métricas de Micrometer.

Después de registrarlo, Actuator puede exponerlo.

---

## Incrementar el contador

Cada vez que se ejecuta el endpoint se llama:

```java
contadorConsultas.increment();
```

Por tanto:

```text
primera consulta
→ COUNT = 1

segunda consulta
→ COUNT = 2

tercera consulta
→ COUNT = 3
```

---

## Generar consultas

Ejecute varias veces:

```bash
curl http://localhost:8080/consulta-contada
```

La respuesta será:

```text
Consulta registrada
```

---

## Consultar la métrica

Después puede consultar:

```bash
curl http://localhost:8080/actuator/metrics/curso.consultas
```

Una respuesta posible es:

```json
{
  "name": "curso.consultas",
  "description": "Cantidad de consultas realizadas al endpoint",
  "measurements": [
    {
      "statistic": "COUNT",
      "value": 5
    }
  ]
}
```

---

## Flujo

```text
Cliente
   ↓
GET /consulta-contada
   ↓
contadorConsultas.increment()
   ↓
MeterRegistry
   ↓
curso.consultas
   ↓
Actuator
```

---

## ¿Cuándo sirve una métrica personalizada?

Puede utilizarse para medir eventos propios del negocio o de la aplicación.

Por ejemplo:

```text
cantidad de compras
cantidad de usuarios registrados
cantidad de búsquedas
cantidad de errores de una operación
cantidad de archivos procesados
```

No todas las métricas útiles son generadas automáticamente por Spring.

---

## ¿Qué debe observar el estudiante?

- Micrometer permite crear métricas personalizadas.
- `Counter` representa eventos acumulativos.
- `MeterRegistry` administra las métricas.
- `increment()` aumenta el contador.
- Las métricas propias también pueden consultarse desde Actuator.
- La observabilidad puede incluir información específica del negocio.

---

## Idea principal

```text
evento de la aplicación
        ↓
Counter
        ↓
MeterRegistry
        ↓
Actuator
        ↓
métrica personalizada
```