# U1_05 - Envío de datos con `POST` y `RequestBody`

Este ejemplo muestra cómo recibir información enviada por un cliente mediante una petición HTTP `POST` y un cuerpo en formato JSON.

---

## Archivo principal

`U1_05_PostRequestBody.java`

```java
package com.lelyliliana.unidad1.ejemplo05;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class U1_05_PostRequestBody {

    @PostMapping("/estudiantes")
    public String crearEstudiante(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String programa = datos.get("programa");

        return "Estudiante recibido: " + nombre + " - " + programa;
    }
}
```

---

## ¿Qué hace este ejemplo?

El cliente envía datos en formato JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

Spring recibe ese contenido mediante:

```java
@RequestBody
```

y lo transforma en:

```java
Map<String, String>
```

---

## ¿Qué es `POST`?

`POST` es un método HTTP utilizado normalmente para enviar información al servidor.

A diferencia de `GET`, los datos principales no tienen que ir dentro de la URL.

En este ejemplo viajan dentro del cuerpo de la petición.

---

## Uso de `@RequestBody`

La anotación:

```java
@RequestBody
```

indica que Spring debe leer el cuerpo de la petición HTTP.

El JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

se convierte en un mapa con valores equivalentes a:

```text
nombre   → Ana
programa → Ingeniería de Sistemas
```

---

## Obtener los valores

El código:

```java
String nombre = datos.get("nombre");
String programa = datos.get("programa");
```

recupera los valores almacenados en el mapa.

Luego se construye la respuesta:

```java
return "Estudiante recibido: " + nombre + " - " + programa;
```

---

## Encabezado `Content-Type`

Cuando se envía JSON, el cliente debe indicar:

```text
Content-Type: application/json
```

Este encabezado informa al servidor sobre el formato del cuerpo recibido.

---

## Probar con herramienta gráfica

Configure:

```text
Método: POST
URL: http://localhost:8080/estudiantes
```

En el cuerpo seleccione formato JSON y envíe:

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
curl -X POST http://localhost:8080/estudiantes \
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
POST /estudiantes
   ↓
JSON
   ↓
@RequestBody
   ↓
Map<String, String>
   ↓
datos.get(...)
   ↓
respuesta
```

---

## Limitación de este enfoque

Utilizar:

```java
Map<String, String>
```

funciona para un ejemplo sencillo, pero no es la forma más organizada de representar datos en una API real.

A medida que aumenta la cantidad de campos, puede resultar difícil:

- validar la información;
- conocer exactamente qué datos se esperan;
- mantener el código;
- reutilizar la estructura.

Por esa razón, en el siguiente ejemplo se utilizará un DTO.

---

## ¿Qué debe observar el estudiante?

- `POST` permite enviar información al servidor.
- `@RequestBody` recibe el cuerpo de la petición.
- JSON puede convertirse en estructuras Java.
- `Content-Type: application/json` indica el formato enviado.
- Un `Map` puede utilizarse para ejemplos simples.
- Para aplicaciones más organizadas conviene usar DTO.

---

## Idea principal

```text
POST
 ↓
JSON
 ↓
@RequestBody
 ↓
Map
 ↓
datos Java
```