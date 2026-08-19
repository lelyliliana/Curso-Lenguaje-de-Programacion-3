# U1_10 - Inyección de dependencias

Este ejemplo muestra cómo una clase puede recibir otra clase que necesita para funcionar, en lugar de crearla directamente.

La inyección de dependencias ayuda a reducir el acoplamiento y facilita el mantenimiento y las pruebas.

---

## Archivos

```text
SaludoService.java
U1_10_InyeccionDependencias.java
```

---

## Servicio

`SaludoService.java`

```java
package com.lelyliliana.unidad1.ejemplo10;

import org.springframework.stereotype.Service;

@Service
public class SaludoService {

    public String generarSaludo(String nombre) {
        return "Hola, " + nombre + ". Bienvenido a Lenguaje de Programación III";
    }
}
```

---

## ¿Qué hace `@Service`?

La anotación:

```java
@Service
```

indica que esta clase representa un servicio administrado por Spring.

Spring crea automáticamente una instancia de `SaludoService` y la mantiene disponible para otras clases que la necesiten.

---

## Controlador

`U1_10_InyeccionDependencias.java`

```java
package com.lelyliliana.unidad1.ejemplo10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_10_InyeccionDependencias {

    private final SaludoService saludoService;

    public U1_10_InyeccionDependencias(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @GetMapping("/saludo-servicio/{nombre}")
    public String saludar(@PathVariable String nombre) {
        return saludoService.generarSaludo(nombre);
    }
}
```

---

## ¿Dónde está la inyección?

La dependencia se declara así:

```java
private final SaludoService saludoService;
```

y se recibe mediante el constructor:

```java
public U1_10_InyeccionDependencias(SaludoService saludoService) {
    this.saludoService = saludoService;
}
```

El controlador necesita un `SaludoService`, pero no lo crea directamente.

---

## Sin inyección de dependencias

Una alternativa sería escribir:

```java
SaludoService saludoService = new SaludoService();
```

En ese caso el controlador sería responsable de crear su propia dependencia.

Eso aumenta el acoplamiento entre las clases.

---

## Con inyección de dependencias

En este ejemplo:

```text
Spring
   ↓
crea SaludoService
   ↓
lo entrega al controlador
   ↓
el controlador lo utiliza
```

El controlador solo declara qué necesita.

Spring se encarga de proporcionar la dependencia.

---

## Separación de responsabilidades

`SaludoService` se encarga de:

```text
generar el saludo
```

Mientras que el controlador se encarga de:

```text
recibir la petición HTTP
y devolver la respuesta
```

Esto permite separar mejor las responsabilidades.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/saludo-servicio/Leli
```

Respuesta esperada:

```text
Hola, Leli. Bienvenido a Lenguaje de Programación III
```

---

## Probar con `curl`

```bash
curl http://localhost:8080/saludo-servicio/Leli
```

Respuesta esperada:

```text
Hola, Leli. Bienvenido a Lenguaje de Programación III
```

---

## Flujo

```text
Cliente
   ↓
GET /saludo-servicio/Leli
   ↓
Controlador
   ↓
SaludoService
   ↓
generarSaludo()
   ↓
respuesta
```

---

## Ventajas de la inyección de dependencias

Ayuda a:

- reducir el acoplamiento;
- separar responsabilidades;
- facilitar cambios de implementación;
- mejorar la mantenibilidad;
- facilitar las pruebas unitarias;
- sustituir dependencias reales por simuladas.

La última ventaja será especialmente importante en los siguientes ejemplos.

---

## ¿Qué debe observar el estudiante?

- Una clase puede depender de otra.
- La dependencia puede recibirse por constructor.
- Spring administra objetos anotados con `@Service`.
- El controlador no necesita crear manualmente el servicio.
- La inyección de dependencias facilita pruebas y mantenimiento.

---

## Idea principal

```text
Controlador
   ↓
necesita
   ↓
Servicio
   ↑
Spring lo crea
e inyecta
```