# U1_08 - Validación de datos con Jakarta Validation

Este ejemplo muestra cómo validar la información recibida por una API antes de procesarla.

La validación permite evitar que lleguen datos incompletos, vacíos o con formatos incorrectos.

---

## Archivos

```text
EstudianteValidadoRequest.java
U1_08_ValidacionDatos.java
```

---

## DTO con reglas de validación

`EstudianteValidadoRequest.java`

```java
package com.lelyliliana.unidad1.ejemplo08;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstudianteValidadoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String correo

) {
}
```

---

## ¿Qué hace este DTO?

Además de representar los datos recibidos, el DTO contiene reglas de validación.

En este ejemplo se validan:

```text
nombre
correo
```

---

## Anotación `@NotBlank`

```java
@NotBlank
```

verifica que el valor:

- no sea `null`;
- no esté vacío;
- no contenga únicamente espacios.

Por ejemplo, estos valores no serían válidos:

```text
""
"   "
null
```

---

## Anotación `@Size`

```java
@Size(min = 3, max = 50)
```

indica que el texto debe tener una longitud mínima de 3 caracteres y máxima de 50.

Por ejemplo:

```text
"A"
```

no sería válido.

Mientras que:

```text
"Ana"
```

sí cumple la restricción.

---

## Anotación `@Email`

```java
@Email
```

verifica que el valor tenga un formato válido de correo electrónico.

Por ejemplo:

```text
ana@example.com
```

es válido.

Mientras que:

```text
correo-invalido
```

no cumple la validación.

---

## Mensajes personalizados

Cada restricción incluye un mensaje.

Por ejemplo:

```java
@NotBlank(message = "El nombre es obligatorio")
```

Si la validación falla, ese mensaje puede utilizarse para informar al cliente cuál fue el problema.

---

## Controlador

`U1_08_ValidacionDatos.java`

```java
package com.lelyliliana.unidad1.ejemplo08;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_08_ValidacionDatos {

    @PostMapping("/estudiantes-validacion")
    public ResponseEntity<String> crearEstudiante(
            @Valid @RequestBody EstudianteValidadoRequest estudiante) {

        String mensaje = "Estudiante registrado: "
                + estudiante.nombre()
                + " - "
                + estudiante.correo();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mensaje);
    }
}
```

---

## Uso de `@Valid`

La parte clave es:

```java
@Valid @RequestBody EstudianteValidadoRequest estudiante
```

`@RequestBody` recibe el JSON.

`@Valid` indica a Spring que debe evaluar las reglas de validación definidas en el DTO.

El flujo es:

```text
JSON
 ↓
@RequestBody
 ↓
EstudianteValidadoRequest
 ↓
@Valid
 ↓
validaciones
```

---

## Probar un caso válido

Con herramienta gráfica:

```text
Método: POST
URL: http://localhost:8080/estudiantes-validacion
```

Cuerpo:

```json
{
  "nombre": "Ana Pérez",
  "correo": "ana@example.com"
}
```

Respuesta esperada:

```text
201 Created
```

y:

```text
Estudiante registrado: Ana Pérez - ana@example.com
```

---

## Probar con `curl`

```bash
curl -i -X POST http://localhost:8080/estudiantes-validacion \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Pérez","correo":"ana@example.com"}'
```

---

## Probar datos inválidos

Por ejemplo:

```json
{
  "nombre": "A",
  "correo": "correo-invalido"
}
```

El nombre no cumple el tamaño mínimo y el correo no tiene un formato válido.

Con `curl`:

```bash
curl -i -X POST http://localhost:8080/estudiantes-validacion \
  -H "Content-Type: application/json" \
  -d '{"nombre":"A","correo":"correo-invalido"}'
```

La API debe rechazar la solicitud.

---

## Flujo de validación

```text
Cliente
   ↓
POST
   ↓
JSON
   ↓
DTO
   ↓
@Valid
   ↓
¿datos válidos?
   ↙          ↘
 sí            no
 ↓             ↓
201          error de
Created      validación
```

---

## ¿Por qué validar?

La validación ayuda a:

- proteger la aplicación de datos incorrectos;
- mantener la calidad de la información;
- evitar errores posteriores;
- proporcionar mensajes claros al cliente;
- centralizar reglas de entrada.

---

## ¿Qué debe observar el estudiante?

- Las reglas de validación pueden declararse directamente en el DTO.
- `@NotBlank` evita valores vacíos.
- `@Size` controla la longitud.
- `@Email` valida el formato del correo.
- `@Valid` activa la evaluación de las restricciones.
- Una API no debería procesar datos inválidos como si fueran correctos.

---

## Idea principal

```text
JSON
 ↓
DTO
 ↓
reglas de validación
 ↓
@Valid
 ↓
aceptar o rechazar
```