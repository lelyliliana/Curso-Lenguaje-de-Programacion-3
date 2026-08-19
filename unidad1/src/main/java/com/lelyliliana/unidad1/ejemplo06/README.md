# U1_06 - Uso de DTO para recibir información

Este ejemplo mejora el manejo de datos recibidos mediante `POST`, reemplazando el uso de `Map<String, String>` por un DTO.

DTO significa:

```text
Data Transfer Object
```

o:

```text
Objeto de Transferencia de Datos
```

---

## Archivos

```text
EstudianteRequest.java
U1_06_PostConDTO.java
```

---

## DTO `EstudianteRequest`

`EstudianteRequest.java`

```java
package com.lelyliliana.unidad1.ejemplo06;

public record EstudianteRequest(
        String nombre,
        String programa
) {
}
```

En este ejemplo se utiliza un `record` de Java.

Un `record` permite representar una estructura de datos de forma compacta.

En lugar de escribir manualmente:

- atributos;
- constructor;
- getters;
- métodos auxiliares;

Java genera gran parte de esa estructura automáticamente.

---

## ¿Qué representa este DTO?

El DTO describe exactamente qué datos espera recibir la API:

```text
nombre
programa
```

Por ejemplo, el JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

puede convertirse en:

```java
EstudianteRequest
```

---

## Controlador

`U1_06_PostConDTO.java`

```java
package com.lelyliliana.unidad1.ejemplo06;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_06_PostConDTO {

    @PostMapping("/estudiantes-dto")
    public String crearEstudiante(@RequestBody EstudianteRequest estudiante) {
        return "Estudiante recibido: "
                + estudiante.nombre()
                + " - "
                + estudiante.programa();
    }
}
```

---

## Uso de `@RequestBody`

Spring recibe el JSON mediante:

```java
@RequestBody EstudianteRequest estudiante
```

y lo convierte automáticamente en un objeto del tipo:

```java
EstudianteRequest
```

---

## Acceder a los valores del `record`

En un `record` se accede a los valores con métodos como:

```java
estudiante.nombre()
```

y:

```java
estudiante.programa()
```

No se utilizan métodos tradicionales como:

```java
getNombre()
```

porque el `record` genera sus propios métodos de acceso.

---

## Comparación con el ejemplo anterior

En el ejemplo anterior:

```java
Map<String, String> datos
```

y luego:

```java
datos.get("nombre")
```

En este ejemplo:

```java
EstudianteRequest estudiante
```

y luego:

```java
estudiante.nombre()
```

La segunda opción deja más claro qué estructura de datos espera la API.

---

## Probar con herramienta gráfica

Configure:

```text
Método: POST
URL: http://localhost:8080/estudiantes-dto
```

Cuerpo JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

Respuesta esperada:

```text
Estudiante recibido: Ana - Ingeniería de Sistemas
```

---

## Probar con `curl`

```bash
curl -X POST http://localhost:8080/estudiantes-dto \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","programa":"Ingeniería de Sistemas"}'
```

Respuesta esperada:

```text
Estudiante recibido: Ana - Ingeniería de Sistemas
```

---

## Flujo

```text
Cliente
   ↓
POST /estudiantes-dto
   ↓
JSON
   ↓
@RequestBody
   ↓
EstudianteRequest
   ↓
record
   ↓
respuesta
```

---

## ¿Por qué usar un DTO?

Un DTO ayuda a:

- definir claramente qué datos recibe la API;
- separar los datos de entrada de otras clases del sistema;
- facilitar validaciones;
- mejorar la legibilidad;
- reducir el acoplamiento;
- mantener mejor el código.

---

## ¿Qué debe observar el estudiante?

- Un DTO representa datos transferidos entre sistemas o capas.
- `record` permite crear estructuras de datos compactas.
- Spring puede convertir JSON directamente a un DTO.
- Los valores de un `record` se consultan con `nombre()` y `programa()`.
- Usar DTO es más claro y mantenible que usar un `Map` para estructuras conocidas.

---

## Idea principal

```text
JSON
 ↓
@RequestBody
 ↓
DTO
 ↓
record
 ↓
datos tipados
```