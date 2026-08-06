# Unidad 1 - Estructura de una API Web

En esta unidad se estudian los fundamentos necesarios para construir y probar una API REST con Java y Spring Boot.

Los ejemplos están organizados de forma progresiva. Se recomienda revisarlos en el orden indicado, ya que cada uno incorpora conceptos nuevos a partir de lo trabajado anteriormente.

Esta unidad toma como referencia los temas planteados en el módulo del curso Lenguaje de Programación III, especialmente los relacionados con API REST, protocolo HTTP, inyección de dependencias y pruebas unitarias, pero utiliza herramientas y prácticas actuales de desarrollo con Java 21 y Spring Boot.

---

## Objetivos de la unidad

Al finalizar los ejemplos de esta unidad, el estudiante estará en capacidad de:

- Comprender la estructura básica de una aplicación desarrollada con Spring Boot.
- Crear endpoints REST utilizando diferentes métodos y mecanismos de recepción de datos.
- Diferenciar el uso de `PathVariable`, `RequestParam` y `RequestBody`.
- Recibir datos en formato JSON.
- Utilizar DTO para representar los datos enviados a una API.
- Trabajar con códigos de estado HTTP mediante `ResponseEntity`.
- Validar los datos recibidos por una API.
- Manejar errores de validación de manera centralizada.
- Comprender el concepto de inyección de dependencias.
- Crear pruebas unitarias utilizando JUnit 5.
- Simular dependencias con Mockito.
- Probar controladores REST utilizando MockMvc.

---

## Tecnologías utilizadas

- Java 21
- Maven
- Spring Boot 3.5
- Spring Web
- Jakarta Validation
- JUnit 5
- Mockito
- MockMvc

---

## Ejemplos disponibles

| Ejemplo | Tema principal |
|---|---|
| `U1_01_PrimeraApi` | Inicio de una aplicación con Spring Boot |
| `U1_02_GetBasico` | Endpoint GET básico |
| `U1_03_PathVariable` | Parámetros incluidos en la URL |
| `U1_04_RequestParam` | Parámetros de consulta |
| `U1_05_PostRequestBody` | Envío de información mediante POST y JSON |
| `U1_06_PostConDTO` | Uso de DTO para recibir información |
| `U1_07_ResponseEntity` | Códigos de estado HTTP |
| `U1_08_ValidacionDatos` | Validación de datos recibidos |
| `U1_09_ManejoErroresValidacion` | Manejo centralizado de errores |
| `U1_10_InyeccionDependencias` | Inyección de dependencias |
| `U1_11` | Pruebas unitarias con JUnit 5 |
| `U1_12` | Simulación de dependencias con Mockito |
| `U1_13_PruebaController` | Pruebas de controladores con MockMvc |

---

## Antes de comenzar

Para ejecutar los ejemplos se recomienda tener instalado:

```text
Java 21
Maven 3.6.3 o superior

Puede verificar las versiones instaladas ejecutando:

```bash
java -version
mvn -version
```

---

# 1. Primera aplicación con Spring Boot

## Archivo principal

`U1_01_PrimeraApi.java`

Este archivo contiene la clase principal de la aplicación. Es el punto de entrada desde el cual se inicia Spring Boot.

```java
package com.lelyliliana.unidad1.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad1")
public class U1_01_PrimeraApi {

    public static void main(String[] args) {
        SpringApplication.run(U1_01_PrimeraApi.class, args);
    }
}
```

## ¿Qué hace este código?

La clase `U1_01_PrimeraApi` inicia la aplicación Spring Boot.

La anotación:

```java
@SpringBootApplication
```

indica que esta es la clase principal de configuración de la aplicación.

En este repositorio se utiliza:

```java
@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad1")
```

porque los ejemplos se encuentran organizados en diferentes paquetes:

```text
ejemplo01
ejemplo02
ejemplo03
...
```

Con `scanBasePackages`, Spring busca componentes dentro de toda la Unidad 1 y no únicamente dentro del paquete `ejemplo01`.

La instrucción:

```java
SpringApplication.run(U1_01_PrimeraApi.class, args);
```

inicia Spring Boot y levanta el servidor web embebido.

---

## Ejecutar la aplicación

Desde la raíz del repositorio se puede ejecutar:

```bash
mvn -pl unidad1 spring-boot:run
```

Si la aplicación inicia correctamente, en la terminal aparecerá información indicando que el servidor se encuentra disponible normalmente en:

```text
http://localhost:8080
```

> Mientras la aplicación esté ejecutándose, la terminal permanecerá ocupada. Para detenerla se puede utilizar `Ctrl + C`.

---

# 2. Endpoint GET básico

## Archivo

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

## ¿Qué se aprende en este ejemplo?

Este ejemplo crea un endpoint utilizando el método HTTP `GET`.

Una petición `GET` se utiliza normalmente para solicitar o consultar información.

La anotación:

```java
@RestController
```

indica que la clase atenderá solicitudes HTTP y que los valores retornados por sus métodos se enviarán directamente como respuesta al cliente.

La anotación:

```java
@GetMapping("/saludo")
```

establece que el método `saludar()` responderá cuando un cliente realice una petición `GET` a la ruta:

```text
/saludo
```

---

## Probar el endpoint

Con Spring Boot en ejecución, abra otra terminal y ejecute:

```bash
curl http://localhost:8080/saludo
```

La respuesta esperada es:

```text
Hola desde Lenguaje de Programación III
```

La comunicación puede representarse de esta forma:

```text
Cliente
   |
   | GET /saludo
   v
API REST
   |
   | 200 OK
   v
"Hola desde Lenguaje de Programación III"
```

---

# 3. Parámetros en la ruta con PathVariable

## Archivo

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

## ¿Qué hace `PathVariable`?

`PathVariable` permite obtener información directamente desde una parte de la URL.

En la ruta:

```text
/saludo/{nombre}
```

la sección:

```text
{nombre}
```

representa un valor variable.

Por ejemplo, si el cliente realiza:

```text
GET /saludo/Leli
```

Spring asignará el valor:

```text
Leli
```

al parámetro:

```java
@PathVariable String nombre
```

---

## Probar el ejemplo

Ejecute:

```bash
curl http://localhost:8080/saludo/Leli
```

La respuesta esperada es:

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

# 4. Parámetros de consulta con RequestParam

## Archivo

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

## ¿Qué hace `RequestParam`?

`RequestParam` permite recibir parámetros enviados después del símbolo `?` en una URL.

Por ejemplo:

```text
/bienvenida?nombre=Leli
```

En este caso:

```text
nombre
```

es el nombre del parámetro y:

```text
Leli
```

es su valor.

Spring recibe ese valor mediante:

```java
@RequestParam String nombre
```

---

## Diferencia entre PathVariable y RequestParam

Con `PathVariable`:

```text
/saludo/Leli
```

Con `RequestParam`:

```text
/bienvenida?nombre=Leli
```

`PathVariable` suele utilizarse cuando el dato identifica directamente un recurso.

`RequestParam` suele utilizarse para filtros, búsquedas u opciones adicionales de una consulta.

---

## Probar el ejemplo

```bash
curl "http://localhost:8080/bienvenida?nombre=Leli"
```

Respuesta esperada:

```text
Bienvenida, Leli
```

---

# 5. Envío de datos con POST y RequestBody

## Archivo

`U1_05_PostRequestBody.java`

En los ejemplos anteriores el cliente solicitaba información mediante `GET`.

Ahora se utilizará `POST`, un método HTTP empleado normalmente para enviar información al servidor.

El cliente enviará datos en formato JSON dentro del cuerpo de la petición.

Ejemplo:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

El controlador recibe esos datos mediante:

```java
@RequestBody
```

## Probar el ejemplo

```bash
curl -X POST http://localhost:8080/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","programa":"Ingeniería de Sistemas"}'
```

La respuesta esperada es:

```text
Estudiante recibido: Ana - Ingeniería de Sistemas
```

### ¿Qué significa cada parte del comando?

```bash
-X POST
```

indica que se utilizará el método HTTP `POST`.

```bash
-H "Content-Type: application/json"
```

informa al servidor que los datos enviados están en formato JSON.

```bash
-d
```

permite enviar los datos dentro del cuerpo de la petición.

---

# 6. Uso de DTO para recibir información

## Archivos

```text
EstudianteRequest.java
U1_06_PostConDTO.java
```

En el ejemplo anterior se utilizó un `Map<String, String>` para recibir los datos.

Aunque esa alternativa funciona, en aplicaciones reales es preferible representar los datos mediante una estructura definida.

Para ello se utiliza un DTO.

DTO significa:

```text
Data Transfer Object
```

o:

```text
Objeto de Transferencia de Datos
```

En este ejemplo se utiliza un `record` de Java:

```java
public record EstudianteRequest(
        String nombre,
        String programa
) {
}
```

Los `record` permiten representar estructuras de datos de forma sencilla y reducen la necesidad de escribir manualmente constructores, getters y otros elementos repetitivos.

El controlador puede recibir directamente:

```java
@RequestBody EstudianteRequest estudiante
```

Spring transforma automáticamente el JSON recibido en un objeto `EstudianteRequest`.

---

## Probar el ejemplo

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

# 7. Códigos de estado HTTP con ResponseEntity

## Archivos

```text
U1_07_ResponseEntity.java
UsuarioRequest.java
```

Una API no debe indicar solamente qué información devuelve.

También debe informar al cliente cuál fue el resultado de la operación mediante un código de estado HTTP.

Algunos códigos comunes son:

| Código | Significado |
|---|---|
| `200 OK` | La operación fue exitosa |
| `201 Created` | Se creó correctamente un recurso |
| `400 Bad Request` | La solicitud contiene datos incorrectos |
| `404 Not Found` | El recurso solicitado no existe |
| `500 Internal Server Error` | Ocurrió un error interno en el servidor |

En este ejemplo se utiliza:

```java
ResponseEntity
```

para controlar tanto el contenido como el código HTTP de la respuesta.

Cuando se crea correctamente un usuario se devuelve:

```java
HttpStatus.CREATED
```

que corresponde al código:

```text
201 Created
```

---

## Probar el ejemplo

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

Se debe observar una respuesta similar a:

```text
HTTP/1.1 201
Content-Type: text/plain;charset=UTF-8

Usuario creado: Ana
```

El elemento más importante en este ejemplo es:

```text
201
```

porque indica que un nuevo recurso fue creado correctamente.

---

# 8. Validación de datos

## Archivos

```text
EstudianteValidadoRequest.java
U1_08_ValidacionDatos.java
```

Una API no debería aceptar cualquier dato sin verificarlo.

Por ejemplo:

- un nombre podría llegar vacío;
- un correo podría tener un formato incorrecto;
- un texto podría superar el tamaño permitido.

Para validar la información se utiliza Jakarta Validation.

En este ejemplo aparecen anotaciones como:

```java
@NotBlank
```

verifica que el valor no sea `null`, vacío o compuesto únicamente por espacios.

```java
@Size(min = 3, max = 50)
```

establece una longitud mínima y máxima.

```java
@Email
```

verifica que el valor tenga un formato válido de correo electrónico.

En el controlador se utiliza:

```java
@Valid
```

para indicar que Spring debe ejecutar las validaciones definidas en el DTO.

---

## Probar un caso válido

```bash
curl -i -X POST http://localhost:8080/estudiantes-validacion \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Pérez","correo":"ana@example.com"}'
```

Respuesta esperada:

```text
HTTP/1.1 201
```

y:

```text
Estudiante registrado: Ana Pérez - ana@example.com
```

---

## Probar un caso inválido

```bash
curl -i -X POST http://localhost:8080/estudiantes-validacion \
  -H "Content-Type: application/json" \
  -d '{"nombre":"A","correo":"correo-invalido"}'
```

En este caso existen dos problemas:

```text
nombre
→ contiene menos de 3 caracteres

correo
→ no tiene un formato válido
```

El servidor debe rechazar la solicitud.

---

# 9. Manejo centralizado de errores de validación

## Archivo

`U1_09_ManejoErroresValidacion.java`

Cuando una validación falla, Spring genera una excepción.

En lugar de entregar al cliente una respuesta técnica difícil de interpretar, podemos controlar esa excepción y devolver solamente información útil.

La anotación:

```java
@RestControllerAdvice
```

permite crear una clase que gestione errores producidos por diferentes controladores de la aplicación.

La anotación:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
```

indica que el método atenderá específicamente errores relacionados con validaciones fallidas.

El resultado para el caso inválido anterior será similar a:

```json
{
  "nombre": "El nombre debe tener entre 3 y 50 caracteres",
  "correo": "El correo no tiene un formato válido"
}
```

y el código HTTP será:

```text
400 Bad Request
```

Esto facilita que una aplicación cliente pueda identificar qué campos presentan problemas.

---

# 10. Inyección de dependencias

## Archivos

```text
SaludoService.java
U1_10_InyeccionDependencias.java
```

La inyección de dependencias permite que una clase utilice otra clase sin tener que crearla directamente.

En este ejemplo el controlador necesita utilizar:

```java
SaludoService
```

Sin inyección de dependencias podría escribirse algo como:

```java
SaludoService servicio = new SaludoService();
```

Sin embargo, esto genera mayor acoplamiento entre las clases.

En cambio, utilizamos inyección mediante constructor:

```java
private final SaludoService saludoService;

public U1_10_InyeccionDependencias(SaludoService saludoService) {
    this.saludoService = saludoService;
}
```

Spring crea automáticamente el objeto `SaludoService` porque la clase está marcada con:

```java
@Service
```

y posteriormente lo entrega al controlador.

Puede representarse así:

```text
Spring
   |
   | crea
   v
SaludoService
   |
   | inyecta
   v
U1_10_InyeccionDependencias
```

---

## Probar el ejemplo

```bash
curl http://localhost:8080/saludo-servicio/Leli
```

Respuesta:

```text
Hola, Leli. Bienvenido a Lenguaje de Programación III
```

---

# 11. Pruebas unitarias con JUnit 5

## Archivos

Código que será probado:

```text
src/main/java/.../ejemplo11/Calculadora.java
```

Prueba:

```text
src/test/java/.../ejemplo11/CalculadoraTest.java
```

Una prueba unitaria permite comprobar pequeñas partes del programa de forma aislada.

La clase `Calculadora` contiene métodos sencillos para:

```text
sumar
dividir
```

La prueba utiliza JUnit 5.

La anotación:

```java
@Test
```

identifica un método como una prueba.

### `assertEquals`

```java
assertEquals(10, resultado);
```

verifica que el resultado obtenido coincida con el esperado.

### `assertThrows`

```java
assertThrows(
        ArithmeticException.class,
        () -> calculadora.dividir(10, 0)
);
```

verifica que determinada operación produzca una excepción.

En este caso se espera que intentar dividir entre cero genere:

```text
ArithmeticException
```

---

## Ejecutar las pruebas

No es necesario levantar el servidor Spring Boot.

Desde la raíz del repositorio ejecute:

```bash
mvn -pl unidad1 test
```

Si todas las pruebas son correctas aparecerá un resultado similar a:

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

y:

```text
BUILD SUCCESS
```

---

# 12. Simulación de dependencias con Mockito

## Archivos

Código principal:

```text
RepositorioEstudiantes.java
EstudianteService.java
```

Prueba:

```text
EstudianteServiceTest.java
```

En algunas situaciones la clase que deseamos probar depende de otros elementos.

Por ejemplo, un servicio podría depender de:

- una base de datos;
- un repositorio;
- una API externa;
- otro servicio.

No siempre queremos utilizar esas dependencias reales durante una prueba unitaria.

Mockito permite crear objetos simulados llamados:

```text
mocks
```

En este ejemplo se crea un repositorio simulado:

```java
RepositorioEstudiantes repositorio =
        mock(RepositorioEstudiantes.class);
```

Luego se define cómo debe comportarse:

```java
when(repositorio.existePorId(1L))
        .thenReturn(true);
```

Eso significa:

```text
Cuando se consulte si existe el estudiante con ID 1,
responde true.
```

No existe una base de datos real.

Mockito está simulando el comportamiento del repositorio.

Esto permite probar únicamente la lógica de:

```text
EstudianteService
```

---

# 13. Prueba de un controlador REST con MockMvc

## Archivos

Controlador:

```text
U1_13_PruebaController.java
```

Prueba:

```text
U1_13_PruebaControllerTest.java
```

Hasta ahora los endpoints se probaron manualmente utilizando:

```text
curl
```

También es posible crear pruebas automáticas para verificar el comportamiento de un controlador.

Para ello Spring proporciona:

```java
MockMvc
```

MockMvc permite simular solicitudes HTTP sin necesidad de iniciar manualmente el servidor.

La anotación:

```java
@WebMvcTest(U1_13_PruebaController.class)
```

indica que la prueba se concentrará en la capa web y específicamente en ese controlador.

En esta estructura de ejemplos también se utiliza:

```java
@ContextConfiguration(classes = U1_01_PrimeraApi.class)
```

para indicar explícitamente cuál es la configuración principal de Spring Boot que debe utilizar la prueba.

La petición simulada es:

```java
mockMvc.perform(get("/estado-api"))
```

Luego se verifica que el estado HTTP sea correcto:

```java
.andExpect(status().isOk())
```

y que el contenido de la respuesta coincida con lo esperado:

```java
.andExpect(content().string("API funcionando correctamente"));
```

---

## Ejecutar todas las pruebas

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 test
```

Actualmente la unidad contiene:

```text
6 pruebas
```

Si todas funcionan correctamente debe aparecer:

```text
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```

seguido de:

```text
BUILD SUCCESS
```

---

# Resumen de conceptos

Al finalizar esta unidad se debe poder reconocer la siguiente evolución:

```text
Spring Boot
    ↓
API REST
    ↓
GET
    ↓
PathVariable y RequestParam
    ↓
POST + JSON
    ↓
DTO
    ↓
códigos HTTP
    ↓
validación
    ↓
manejo de errores
    ↓
inyección de dependencias
    ↓
pruebas unitarias
    ↓
Mockito
    ↓
pruebas de controladores
```

---

## Comandos principales

### Ejecutar la aplicación

```bash
mvn -pl unidad1 spring-boot:run
```

### Detener la aplicación

```text
Ctrl + C
```

### Ejecutar las pruebas

```bash
mvn -pl unidad1 test
```

---

## Recomendación de estudio

No se recomienda comenzar directamente por los últimos ejemplos.

La secuencia está diseñada para que cada concepto se apoye en lo aprendido anteriormente.

El orden recomendado es:

```text
01 → 02 → 03 → 04 → 05 → 06 → 07
   → 08 → 09 → 10 → 11 → 12 → 13
```

Antes de continuar con la Unidad 2, asegúrese de comprender:

- qué representa un endpoint;
- la diferencia entre `GET` y `POST`;
- cómo se reciben datos mediante URL o JSON;
- qué función cumple un DTO;
- qué significan los principales códigos HTTP;
- para qué se validan los datos;
- qué problema resuelve la inyección de dependencias;
- para qué sirven JUnit, Mockito y MockMvc.