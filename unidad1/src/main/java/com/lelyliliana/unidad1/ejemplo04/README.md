# U1_04 - Parámetros de consulta con `RequestParam`

Este ejemplo muestra cómo recibir parámetros enviados en la URL utilizando `@RequestParam`.

---

## Archivo principal

`U1_04_RequestParam.java`

```java
package com.lelyliliana.unidad1.ejemplo04;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_04_RequestParam {

    @GetMapping("/bienvenida")
    public String bienvenida(@RequestParam String nombre) {
        return "Bienvenida, " + nombre;
    }
}
```

---

## ¿Qué hace este ejemplo?

El endpoint recibe un parámetro de consulta.

Por ejemplo:

```text
GET /bienvenida?nombre=Leli
```

En esta URL:

```text
nombre
```

es el nombre del parámetro y:

```text
Leli
```

es el valor enviado.

---

## Uso de `@RequestParam`

La anotación:

```java
@RequestParam String nombre
```

permite obtener un parámetro enviado después del símbolo:

```text
?
```

En la petición:

```text
/bienvenida?nombre=Leli
```

Spring asigna:

```java
nombre = "Leli";
```

---

## Estructura de un parámetro de consulta

Una URL con parámetros puede tener esta forma:

```text
/ruta?parametro=valor
```

Por ejemplo:

```text
/bienvenida?nombre=Ana
```

También pueden enviarse varios parámetros:

```text
/buscar?nombre=Ana&programa=Sistemas
```

El símbolo:

```text
&
```

se utiliza para separar diferentes parámetros.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/bienvenida
```

Agregue un parámetro:

```text
Clave: nombre
Valor: Leli
```

---

## Probar con `curl`

```bash
curl "http://localhost:8080/bienvenida?nombre=Leli"
```

Respuesta esperada:

```text
Bienvenida, Leli
```

---

## Diferencia entre `PathVariable` y `RequestParam`

Con `PathVariable`:

```text
/saludo/Leli
```

Con `RequestParam`:

```text
/bienvenida?nombre=Leli
```

`PathVariable` suele utilizarse cuando el valor forma parte de la identificación del recurso.

`RequestParam` suele utilizarse para:

- filtros;
- búsquedas;
- opciones;
- criterios adicionales.

---

## Flujo

```text
GET /bienvenida?nombre=Leli
        ↓
@RequestParam
        ↓
nombre = "Leli"
        ↓
bienvenida()
        ↓
respuesta
```

---

## ¿Qué debe observar el estudiante?

- Los parámetros de consulta aparecen después de `?`.
- `@RequestParam` permite recibir esos valores.
- Los parámetros pueden utilizarse para filtros o búsquedas.
- `PathVariable` y `RequestParam` no cumplen exactamente la misma función.

---

## Idea principal

```text
URL
 ↓
parámetro de consulta
 ↓
@RequestParam
 ↓
variable Java
 ↓
respuesta
```