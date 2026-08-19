# U1_07 - Códigos HTTP con `ResponseEntity`

Este ejemplo muestra cómo controlar el código de estado HTTP y el contenido de una respuesta utilizando `ResponseEntity`.

---

## Archivos

```text
UsuarioRequest.java
U1_07_ResponseEntity.java
```

---

## DTO `UsuarioRequest`

`UsuarioRequest.java`

```java
package com.lelyliliana.unidad1.ejemplo07;

public record UsuarioRequest(
        String nombre
) {
}
```

Este DTO representa los datos que recibe la API para crear un usuario.

El JSON esperado es:

```json
{
  "nombre": "Ana"
}
```

---

## Controlador

`U1_07_ResponseEntity.java`

```java
package com.lelyliliana.unidad1.ejemplo07;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_07_ResponseEntity {

    @PostMapping("/usuarios")
    public ResponseEntity<String> crearUsuario(
            @RequestBody UsuarioRequest usuario) {

        String mensaje = "Usuario creado: " + usuario.nombre();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mensaje);
    }
}
```

---

## ¿Qué hace este ejemplo?

El cliente realiza una petición:

```text
POST /usuarios
```

y envía un cuerpo JSON:

```json
{
  "nombre": "Ana"
}
```

La API recibe los datos, construye un mensaje y devuelve una respuesta con:

```text
201 Created
```

---

## Uso de `ResponseEntity`

El método retorna:

```java
ResponseEntity<String>
```

Esto permite controlar dos elementos:

```text
código de estado HTTP
+
contenido de la respuesta
```

---

## Código `201 Created`

La instrucción:

```java
HttpStatus.CREATED
```

representa el código:

```text
201 Created
```

Este código se utiliza cuando una solicitud crea correctamente un nuevo recurso.

---

## Construcción de la respuesta

El código:

```java
return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(mensaje);
```

puede interpretarse así:

```text
status(...)
→ define el código HTTP

body(...)
→ define el contenido de la respuesta
```

---

## Algunos códigos HTTP comunes

| Código | Significado |
|---|---|
| `200 OK` | La operación fue exitosa |
| `201 Created` | Se creó correctamente un recurso |
| `204 No Content` | La operación fue exitosa y no se devuelve contenido |
| `400 Bad Request` | La solicitud contiene datos incorrectos |
| `404 Not Found` | El recurso no existe |
| `500 Internal Server Error` | Ocurrió un error interno en el servidor |

---

## Probar con herramienta gráfica

Configure:

```text
Método: POST
URL: http://localhost:8080/usuarios
```

Cuerpo JSON:

```json
{
  "nombre": "Ana"
}
```

La respuesta debe indicar:

```text
201 Created
```

y mostrar:

```text
Usuario creado: Ana
```

---

## Probar con `curl`

Utilice:

```bash
curl -i -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'
```

La opción:

```text
-i
```

permite visualizar también los encabezados HTTP.

Una respuesta esperada es:

```text
HTTP/1.1 201
Content-Type: text/plain;charset=UTF-8

Usuario creado: Ana
```

---

## ¿Por qué es importante el código HTTP?

Una API no debe indicar únicamente qué contenido devuelve.

También debe informar al cliente cuál fue el resultado de la operación.

Por ejemplo:

```text
201
→ el recurso fue creado

404
→ el recurso no existe

400
→ la solicitud contiene un problema
```

Esto permite que otras aplicaciones interpreten correctamente la respuesta.

---

## Flujo

```text
Cliente
   ↓
POST /usuarios
   ↓
JSON
   ↓
UsuarioRequest
   ↓
crearUsuario()
   ↓
ResponseEntity
   ↓
201 Created
   +
mensaje
```

---

## ¿Qué debe observar el estudiante?

- `ResponseEntity` permite controlar la respuesta HTTP.
- El cuerpo y el código HTTP son conceptos diferentes.
- `201 Created` es apropiado cuando se crea un recurso.
- Los códigos HTTP permiten comunicar el resultado de una operación.
- Una API bien diseñada debe utilizar estados HTTP coherentes.

---

## Idea principal

```text
operación
   ↓
resultado
   ↓
ResponseEntity
   ↓
código HTTP
+
contenido
```