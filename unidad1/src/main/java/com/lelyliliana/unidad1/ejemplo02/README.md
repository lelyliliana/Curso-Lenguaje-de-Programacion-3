# U1_02 - Endpoint GET básico

Este ejemplo muestra cómo crear un endpoint REST sencillo utilizando el método HTTP `GET`.

---

## Archivo principal

`U1_02_GetBasico.java`

```java
package com.lelyliliana.unidad1.ejemplo02;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_02_GetBasico {

    @GetMapping("/saludo")
    public String saludar() {
        return "Hola desde Lenguaje de Programación III";
    }
}
```

---

## ¿Qué hace este ejemplo?

La clase define un endpoint accesible mediante:

```text
GET /saludo
```

Cuando un cliente realiza esa petición, el servidor responde:

```text
Hola desde Lenguaje de Programación III
```

---

## Anotación `@RestController`

La anotación:

```java
@RestController
```

indica que la clase atenderá solicitudes HTTP.

Los valores retornados por sus métodos se enviarán directamente como respuesta al cliente.

---

## Anotación `@GetMapping`

La anotación:

```java
@GetMapping("/saludo")
```

relaciona una petición HTTP `GET` con el método:

```java
saludar()
```

Por tanto:

```text
GET /saludo
```

ejecuta:

```java
public String saludar()
```

---

## ¿Qué es `GET`?

`GET` es un método HTTP utilizado normalmente para consultar o solicitar información.

En este ejemplo no se envían datos al servidor; únicamente se solicita una respuesta.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/saludo
```

No es necesario enviar cuerpo JSON.

---

## Probar con `curl`

```bash
curl http://localhost:8080/saludo
```

Respuesta esperada:

```text
Hola desde Lenguaje de Programación III
```

---

## Flujo

```text
Cliente
   ↓
GET /saludo
   ↓
@GetMapping
   ↓
saludar()
   ↓
String
   ↓
Respuesta HTTP
```

---

## ¿Qué debe observar el estudiante?

- `@RestController` identifica una clase que atiende solicitudes HTTP.
- `@GetMapping` permite asociar una ruta con una petición `GET`.
- Un endpoint puede devolver texto directamente.
- Spring Boot convierte el valor retornado por el método en la respuesta HTTP.

---

## Idea principal

```text
GET
 ↓
endpoint
 ↓
método Java
 ↓
respuesta
```