# U1_03 - Parámetros en la ruta con `PathVariable`

Este ejemplo muestra cómo recibir un valor directamente desde la URL utilizando `@PathVariable`.

---

## Archivo principal

`U1_03_PathVariable.java`

```java
package com.lelyliliana.unidad1.ejemplo03;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_03_PathVariable {

    @GetMapping("/saludo/{nombre}")
    public String saludarPorNombre(@PathVariable String nombre) {
        return "Hola, " + nombre;
    }
}
```

---

## ¿Qué hace este ejemplo?

El endpoint recibe un valor como parte de la propia ruta.

Por ejemplo:

```text
GET /saludo/Leli
```

En este caso:

```text
Leli
```

es el valor recibido por la variable:

```java
String nombre
```

---

## Ruta dinámica

La anotación:

```java
@GetMapping("/saludo/{nombre}")
```

contiene:

```text
{nombre}
```

Esto indica que esa parte de la URL es variable.

Por ejemplo:

```text
/saludo/Ana
/saludo/Carlos
/saludo/Leli
```

utilizan el mismo endpoint, pero con valores diferentes.

---

## Uso de `@PathVariable`

La anotación:

```java
@PathVariable String nombre
```

permite obtener el valor incluido en la URL.

Si la petición es:

```text
/saludo/Ana
```

Spring asigna:

```java
nombre = "Ana";
```

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/saludo/Leli
```

---

## Probar con `curl`

```bash
curl http://localhost:8080/saludo/Leli
```

Respuesta esperada:

```text
Hola, Leli
```

Otro ejemplo:

```bash
curl http://localhost:8080/saludo/Carlos
```

Respuesta:

```text
Hola, Carlos
```

---

## Flujo

```text
GET /saludo/Leli
        ↓
{nombre}
        ↓
@PathVariable
        ↓
nombre = "Leli"
        ↓
saludarPorNombre()
        ↓
respuesta
```

---

## ¿Cuándo utilizar `PathVariable`?

Se utiliza cuando el valor forma parte natural de la identificación o estructura del recurso.

Por ejemplo:

```text
/usuarios/10
/productos/25
/estudiantes/4
```

En esos casos, el valor identifica directamente el recurso que se desea consultar.

---

## ¿Qué debe observar el estudiante?

- Una ruta puede contener partes variables.
- `@PathVariable` obtiene valores directamente desde la URL.
- El mismo endpoint puede responder a diferentes valores.
- El dato recibido puede utilizarse dentro del método Java.

---

## Idea principal

```text
URL
 ↓
PathVariable
 ↓
variable Java
 ↓
respuesta personalizada
```