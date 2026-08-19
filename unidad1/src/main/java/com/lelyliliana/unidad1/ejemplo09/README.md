# U1_09 - Manejo centralizado de errores de validación

Este ejemplo muestra cómo personalizar la respuesta cuando una validación falla.

En el ejemplo anterior, las reglas de validación ya rechazaban los datos incorrectos. Ahora se mejora la forma en que esos errores se presentan al cliente.

---

## Archivo principal

`U1_09_ManejoErroresValidacion.java`

```java
package com.lelyliliana.unidad1.ejemplo09;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class U1_09_ManejoErroresValidacion {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errores.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }
}
```

---

## ¿Qué problema resuelve este ejemplo?

Cuando una validación falla, Spring genera una excepción.

Sin un manejo personalizado, la respuesta puede contener información técnica que no resulta clara para quien consume la API.

Este ejemplo transforma esos errores en una respuesta más sencilla.

---

## Anotación `@RestControllerAdvice`

```java
@RestControllerAdvice
```

indica que la clase puede manejar excepciones producidas por distintos controladores REST.

Esto permite centralizar el manejo de errores en una sola clase.

---

## Anotación `@ExceptionHandler`

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
```

indica que el método se ejecutará cuando ocurra una excepción de tipo:

```text
MethodArgumentNotValidException
```

Esta excepción aparece cuando una validación con `@Valid` no se cumple.

---

## Obtener los errores de validación

La instrucción:

```java
ex.getBindingResult()
        .getFieldErrors()
```

recupera los errores asociados a los campos validados.

Luego cada error se agrega al mapa:

```java
errores.put(
        error.getField(),
        error.getDefaultMessage()
);
```

Por ejemplo:

```text
campo: nombre
mensaje: El nombre debe tener entre 3 y 50 caracteres
```

---

## Respuesta generada

El método devuelve:

```java
ResponseEntity<Map<String, String>>
```

con estado:

```java
HttpStatus.BAD_REQUEST
```

equivalente a:

```text
400 Bad Request
```

---

## Probar con datos inválidos

Utilice el endpoint del ejemplo anterior:

```text
POST /estudiantes-validacion
```

Cuerpo:

```json
{
  "nombre": "A",
  "correo": "correo-invalido"
}
```

Con `curl`:

```bash
curl -i -X POST http://localhost:8080/estudiantes-validacion \
  -H "Content-Type: application/json" \
  -d '{"nombre":"A","correo":"correo-invalido"}'
```

Respuesta esperada:

```text
HTTP/1.1 400
```

y un cuerpo similar a:

```json
{
  "nombre": "El nombre debe tener entre 3 y 50 caracteres",
  "correo": "El correo no tiene un formato válido"
}
```

---

## Flujo

```text
Cliente
   ↓
datos inválidos
   ↓
@Valid
   ↓
MethodArgumentNotValidException
   ↓
@ExceptionHandler
   ↓
construcción de errores
   ↓
400 Bad Request
   ↓
JSON claro
```

---

## ¿Por qué centralizar errores?

Permite:

- evitar respuestas técnicas innecesarias;
- mantener un formato consistente;
- reutilizar el manejo de errores;
- separar la lógica del controlador de la gestión de excepciones;
- entregar mensajes más claros al cliente.

---

## ¿Qué debe observar el estudiante?

- Las validaciones pueden generar excepciones.
- `@RestControllerAdvice` centraliza el manejo de errores.
- `@ExceptionHandler` permite capturar tipos específicos de excepción.
- `400 Bad Request` es apropiado cuando la solicitud contiene datos inválidos.
- Una API puede devolver errores en formato JSON.

---

## Idea principal

```text
error de validación
        ↓
excepción
        ↓
manejador central
        ↓
respuesta clara
        ↓
400 Bad Request
```