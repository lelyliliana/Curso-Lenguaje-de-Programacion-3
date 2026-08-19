# U1_13 - Prueba de un controlador REST con MockMvc

Este ejemplo muestra cómo probar automáticamente un controlador REST sin necesidad de iniciar manualmente el servidor y realizar peticiones con `curl`.

Para ello se utiliza `MockMvc`.

---

## Archivos

Controlador:

```text
src/main/java/com/lelyliliana/unidad1/ejemplo13/U1_13_PruebaController.java
```

Prueba:

```text
src/test/java/com/lelyliliana/unidad1/ejemplo13/U1_13_PruebaControllerTest.java
```

---

## Controlador

`U1_13_PruebaController.java`

```java
package com.lelyliliana.unidad1.ejemplo13;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U1_13_PruebaController {

    @GetMapping("/estado-api")
    public String consultarEstado() {
        return "API funcionando correctamente";
    }
}
```

Este controlador expone:

```text
GET /estado-api
```

y devuelve:

```text
API funcionando correctamente
```

---

## ¿Qué queremos probar?

Queremos comprobar automáticamente que:

1. el endpoint `/estado-api` responda;
2. el código HTTP sea `200 OK`;
3. el contenido devuelto sea exactamente el esperado.

---

## Clase de prueba

`U1_13_PruebaControllerTest.java`

```java
package com.lelyliliana.unidad1.ejemplo13;

import com.lelyliliana.unidad1.ejemplo01.U1_01_PrimeraApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(U1_13_PruebaController.class)
@ContextConfiguration(classes = U1_01_PrimeraApi.class)
class U1_13_PruebaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void consultarEstadoRetornaRespuestaCorrecta() throws Exception {

        mockMvc.perform(get("/estado-api"))
                .andExpect(status().isOk())
                .andExpect(content().string("API funcionando correctamente"));
    }
}
```

---

## Anotación `@WebMvcTest`

La anotación:

```java
@WebMvcTest(U1_13_PruebaController.class)
```

indica que la prueba se concentra en la capa web y específicamente en:

```text
U1_13_PruebaController
```

No es necesario cargar toda la aplicación completa.

---

## `@ContextConfiguration`

En este repositorio la clase principal está ubicada en el paquete:

```text
ejemplo01
```

mientras que esta prueba está en:

```text
ejemplo13
```

Por eso se declara explícitamente:

```java
@ContextConfiguration(classes = U1_01_PrimeraApi.class)
```

Esto indica cuál es la configuración principal de Spring Boot que debe utilizarse durante la prueba.

> Esta necesidad aparece por la organización pedagógica del repositorio en paquetes separados para cada ejemplo.

---

## ¿Qué es `MockMvc`?

`MockMvc` permite simular peticiones HTTP dentro de una prueba.

No es necesario:

```text
iniciar Spring Boot
abrir otra terminal
ejecutar curl
```

La propia prueba realiza la petición.

---

## Inyección de `MockMvc`

La clase declara:

```java
@Autowired
private MockMvc mockMvc;
```

Spring proporciona automáticamente el objeto `MockMvc` preparado para realizar las pruebas.

---

## Simular una petición GET

La instrucción:

```java
mockMvc.perform(get("/estado-api"))
```

simula:

```text
GET /estado-api
```

---

## Verificar el código HTTP

La prueba utiliza:

```java
.andExpect(status().isOk())
```

Esto comprueba que la respuesta tenga:

```text
200 OK
```

---

## Verificar el contenido

También se verifica:

```java
.andExpect(
    content().string("API funcionando correctamente")
);
```

Esto comprueba que el cuerpo de la respuesta sea exactamente:

```text
API funcionando correctamente
```

---

## Flujo de la prueba

```text
JUnit
 ↓
MockMvc
 ↓
GET /estado-api
 ↓
Controlador
 ↓
respuesta
 ↓
verificar status
 ↓
verificar contenido
```

---

## Ejecutar las pruebas

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 test
```

En conjunto con los ejemplos anteriores, la Unidad 1 ejecuta actualmente seis pruebas automatizadas.

Una salida correcta debe mostrar:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

y:

```text
BUILD SUCCESS
```

---

## Diferencia entre prueba manual y automática

Prueba manual:

```text
iniciar servidor
 ↓
ejecutar curl
 ↓
observar respuesta
```

Prueba automatizada:

```text
mvn test
 ↓
MockMvc
 ↓
simula petición
 ↓
comprueba respuesta
```

Ambas son útiles, pero las pruebas automatizadas permiten repetir las verificaciones rápidamente cada vez que cambia el código.

---

## ¿Qué debe observar el estudiante?

- `MockMvc` permite probar endpoints sin levantar manualmente el servidor.
- `@WebMvcTest` se concentra en la capa web.
- `get()` simula una petición HTTP.
- `status().isOk()` verifica el código `200`.
- `content().string()` verifica el contenido de la respuesta.
- Las pruebas de controladores pueden ejecutarse automáticamente con Maven.

---

## Idea principal

```text
endpoint REST
     ↓
MockMvc
     ↓
petición simulada
     ↓
respuesta
     ↓
validaciones automáticas
```