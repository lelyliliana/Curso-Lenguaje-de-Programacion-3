# Unidad 2 - Comunicación con servicios externos

En esta unidad se estudia cómo una aplicación Spring Boot puede almacenar información de manera persistente y comunicarse con servicios externos.

Los ejemplos están organizados de forma progresiva. Se recomienda seguir el orden indicado, ya que primero se introduce el mapeo objeto-relacional y posteriormente se construyen operaciones CRUD, consultas, relaciones entre entidades, consumo de APIs externas y persistencia con MySQL.

Esta unidad toma como referencia los contenidos del módulo de Lenguaje de Programación III relacionados con persistencia, ORM, Hibernate, CRUD y comunicación con APIs externas, utilizando una implementación actual con Java 21, Spring Boot, Spring Data JPA y Hibernate.

---

## Objetivos de la unidad

Al finalizar los ejemplos de esta unidad, el estudiante estará en capacidad de:

- Comprender el concepto de persistencia de datos.
- Identificar la función de un ORM.
- Crear entidades mediante JPA.
- Comprender la relación entre una clase Java y una tabla de base de datos.
- Utilizar `JpaRepository`.
- Realizar operaciones CRUD.
- Consultar registros por identificador.
- Crear consultas derivadas con Spring Data JPA.
- Modelar relaciones entre entidades.
- Comprender el uso de `@ManyToOne`.
- Consumir una API externa desde Spring Boot.
- Convertir respuestas JSON externas en objetos Java.
- Manejar errores provenientes de servicios externos.
- Diferenciar una base de datos en memoria de una base persistente.
- Conectar una aplicación Spring Boot con MySQL.

---

## Tecnologías utilizadas

- Java 21
- Maven
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- MySQL
- MySQL Connector/J
- RestClient
- Jackson

---

## Ejemplos disponibles

| Ejemplo | Tema principal |
|---|---|
| `U2_01_PrimeraEntidadJPA` | Primera entidad JPA y repositorio |
| `U2_02_GuardarEstudiante` | Guardar registros con `save()` |
| `U2_03_ListarEstudiantes` | Consultar todos los registros con `findAll()` |
| `U2_04_BuscarEstudiantePorId` | Consultar por identificador |
| `U2_05_ActualizarEstudiante` | Actualización de registros con `PUT` |
| `U2_06_EliminarEstudiante` | Eliminación de registros con `DELETE` |
| `U2_07_BuscarPorPrograma` | Consultas derivadas con Spring Data JPA |
| `U2_08_RelacionManyToOne` | Relaciones entre entidades |
| `U2_09_ConsumirApiExterna` | Consumo de la API pública de GitHub |
| `U2_10_PersistenciaMySQL` | Persistencia utilizando MySQL |

---

## Antes de comenzar

Para ejecutar los ejemplos se recomienda tener instalado:

```text
Java 21
Maven 3.6.3 o superior
```

Para el ejemplo de MySQL también se requiere:

```text
MySQL Server 8 o superior
```

Puede verificar las versiones ejecutando:

```bash
java -version
mvn -version
mysql --version
```

---

## Formas de probar los ejemplos

Los endpoints REST pueden probarse de diferentes maneras.

### Opción 1 - Herramienta gráfica

Puede utilizar una aplicación para pruebas de APIs como Postman o Bruno.

Este tipo de herramienta permite visualizar de forma clara:

- método HTTP;
- URL;
- parámetros;
- encabezados;
- cuerpo JSON;
- código de estado;
- respuesta del servidor.

### Opción 2 - Terminal

También puede utilizar `curl`.

Los comandos correspondientes a cada ejemplo se encuentran documentados en las siguientes secciones.

Ambas opciones realizan las mismas peticiones HTTP. La elección depende de la herramienta con la que se sienta más cómodo.

---

## 1. Primera entidad JPA

### Archivos

```text
Estudiante.java
EstudianteRepository.java
U2_01_PrimeraEntidadJPA.java
```

Este primer ejemplo introduce el concepto de persistencia utilizando JPA e Hibernate.

El objetivo es comprender cómo una clase Java puede representar una tabla de base de datos y cómo Spring Data JPA permite acceder a los datos sin escribir manualmente todas las operaciones SQL básicas.

---

### Clase principal

`U2_01_PrimeraEntidadJPA.java`

```java
package com.lelyliliana.unidad2.ejemplo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad2")
@EntityScan(basePackages = "com.lelyliliana.unidad2")
@EnableJpaRepositories(basePackages = "com.lelyliliana.unidad2")
public class U2_01_PrimeraEntidadJPA {

    public static void main(String[] args) {
        SpringApplication.run(U2_01_PrimeraEntidadJPA.class, args);
    }
}
```

### ¿Qué hace esta clase?

Esta es la clase principal de Spring Boot para la Unidad 2.

La anotación:

```java
@SpringBootApplication
```

indica que la aplicación debe iniciar utilizando la configuración automática de Spring Boot.

En este repositorio los ejemplos están distribuidos en distintos paquetes:

```text
ejemplo01
ejemplo02
ejemplo03
...
```

Por esa razón se utiliza:

```java
scanBasePackages = "com.lelyliliana.unidad2"
```

para que Spring pueda encontrar todos los componentes de la unidad.

También se utilizan:

```java
@EntityScan(basePackages = "com.lelyliliana.unidad2")
```

y:

```java
@EnableJpaRepositories(basePackages = "com.lelyliliana.unidad2")
```

Esto permite que Spring encuentre las entidades JPA y los repositorios aunque estén distribuidos en distintos paquetes.

> Esta configuración responde a la organización pedagógica del repositorio. En un proyecto convencional, la clase principal suele ubicarse en un paquete raíz y Spring puede detectar automáticamente los componentes ubicados debajo de ese paquete.

---

### Entidad `Estudiante`

`Estudiante.java`

```java
@Entity
public class Estudiante {
```

La anotación:

```java
@Entity
```

indica que esta clase representa una entidad que puede almacenarse en una base de datos.

En términos simples:

```text
Clase Java
   ↓
@Entity
   ↓
Tabla en la base de datos
```

La propiedad:

```java
@Id
```

indica cuál atributo será la llave primaria.

En este caso:

```java
private Long id;
```

La anotación:

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

indica que el valor del identificador será generado automáticamente por la base de datos.

Por ejemplo:

```text
1
2
3
4
...
```

Los atributos:

```java
private String nombre;
private String programa;
```

se convierten en columnas de la tabla.

Por tanto, la clase:

```text
Estudiante
```

puede representarse aproximadamente como:

```text
estudiante
----------------------------
id        BIGINT
nombre    VARCHAR
programa  VARCHAR
```

---

### ¿Qué es ORM?

ORM significa:

```text
Object-Relational Mapping
```

o:

```text
Mapeo Objeto-Relacional
```

Es una técnica que permite relacionar objetos de un lenguaje orientado a objetos con estructuras de una base de datos relacional.

En este ejemplo:

```text
Objeto Java
Estudiante
        ↓
JPA / Hibernate
        ↓
Tabla
estudiante
```

Esto evita que el desarrollador tenga que escribir manualmente SQL para cada operación básica.

Hibernate actúa como implementación de JPA y genera las instrucciones SQL necesarias.

---

### Repositorio

`EstudianteRepository.java`

```java
public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {
}
```

Este repositorio trabaja con:

```text
Entidad: Estudiante
Llave primaria: Long
```

Al extender:

```java
JpaRepository
```

Spring Data JPA proporciona automáticamente métodos como:

```text
save()
findAll()
findById()
existsById()
deleteById()
```

No es necesario programar manualmente esas operaciones.

---

### Base de datos H2

Para los primeros ejemplos se utiliza H2.

H2 permite crear una base de datos temporal en memoria, lo cual facilita aprender JPA sin necesidad de configurar inicialmente un servidor externo de bases de datos.

La configuración se encuentra en:

```text
src/main/resources/application.properties
```

La URL utilizada es:

```properties
spring.datasource.url=jdbc:h2:mem:lenguaje3db
```

La palabra:

```text
mem
```

indica que la base se mantiene en memoria.

Esto significa que:

```text
inicia Spring Boot
       ↓
se crea la base
       ↓
se almacenan los datos
       ↓
se detiene Spring Boot
       ↓
los datos desaparecen
```

Esta característica es útil para pruebas y aprendizaje, pero no para información que deba conservarse permanentemente.

---

### Creación automática de tablas

La propiedad:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

permite que Hibernate cree automáticamente las tablas al iniciar la aplicación.

Al detenerla, las tablas se eliminan.

También se utiliza:

```properties
spring.jpa.show-sql=true
```

para mostrar en la terminal las instrucciones SQL generadas por Hibernate.

Por ejemplo, puede observarse una instrucción similar a:

```sql
create table estudiante (
    id bigint generated by default as identity,
    nombre varchar(255),
    programa varchar(255),
    primary key (id)
)
```

Esto permite visualizar directamente cómo una clase Java termina representada como una tabla.

---

### Ejecutar la Unidad 2 con H2

Desde la raíz del repositorio:

```bash
mvn -pl unidad2 spring-boot:run
```

Al iniciar correctamente, Spring Boot estará disponible normalmente en:

```text
http://localhost:8080
```

---

### Consola H2

Mientras se utiliza la configuración H2, puede accederse a:

```text
http://localhost:8080/h2-console
```

La URL JDBC utilizada es:

```text
jdbc:h2:mem:lenguaje3db
```

Usuario:

```text
sa
```

La contraseña se encuentra vacía en esta configuración de aprendizaje.

La consola permite observar las tablas creadas por Hibernate y ejecutar consultas directamente sobre la base temporal.

---

### Idea principal del ejemplo

El flujo de este primer ejemplo puede resumirse así:

```text
Estudiante.java
      ↓
@Entity
      ↓
JPA
      ↓
Hibernate
      ↓
SQL generado
      ↓
Base de datos H2
```

El estudiante debe comprender que JPA no reemplaza la base de datos.

JPA permite trabajar con la información utilizando objetos Java, mientras Hibernate se encarga de traducir esas operaciones al modelo relacional.

---

## 2. Guardar un estudiante con `save()`

### Archivo principal

`U2_02_GuardarEstudiante.java`

```java
package com.lelyliliana.unidad2.ejemplo02;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_02_GuardarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_02_GuardarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @PostMapping("/estudiantes-db")
    public Estudiante guardar(
            @RequestBody Estudiante estudiante) {

        return estudianteRepository.save(estudiante);
    }
}
```

### ¿Qué hace este ejemplo?

Este ejemplo permite guardar un estudiante en la base de datos utilizando una petición HTTP `POST`.

El cliente envía un objeto JSON como:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

Spring convierte automáticamente ese JSON en un objeto Java de tipo:

```java
Estudiante
```

gracias a:

```java
@RequestBody Estudiante estudiante
```

El flujo es:

```text
Cliente
   ↓
POST /estudiantes-db
   ↓
JSON
   ↓
@RequestBody
   ↓
Objeto Estudiante
   ↓
EstudianteRepository
   ↓
save()
   ↓
Hibernate
   ↓
INSERT en la base de datos
```

---

### Inyección del repositorio

La clase recibe:

```java
private final EstudianteRepository estudianteRepository;
```

mediante el constructor:

```java
public U2_02_GuardarEstudiante(
        EstudianteRepository estudianteRepository) {
    this.estudianteRepository = estudianteRepository;
}
```

Spring se encarga de proporcionar automáticamente una implementación de `EstudianteRepository`.

No es necesario crear manualmente un objeto del repositorio.

---

### Método `save()`

La instrucción:

```java
estudianteRepository.save(estudiante)
```

guarda el objeto recibido en la base de datos.

Aunque no escribimos una sentencia SQL manual, Hibernate genera una operación equivalente a:

```sql
insert into estudiante (nombre, programa)
values (?, ?)
```

El método `save()` también devuelve el objeto almacenado.

Después de guardar, el objeto ya contiene el identificador generado por la base de datos.

Por ejemplo:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

### Probar con herramienta gráfica

Configure una petición con:

```text
Método: POST
URL: http://localhost:8080/estudiantes-db
```

En el cuerpo seleccione formato JSON y envíe:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

La respuesta debe incluir el identificador generado.

---

### Probar con `curl`

```bash
curl -X POST http://localhost:8080/estudiantes-db \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","programa":"Ingeniería de Sistemas"}'
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- el cliente envía JSON;
- Spring transforma el JSON en un objeto Java;
- el controlador utiliza el repositorio;
- `save()` persiste el objeto;
- Hibernate genera el SQL;
- la base de datos genera el identificador;
- la API devuelve el objeto almacenado.

---

### Idea principal del ejemplo

```text
JSON
 ↓
Estudiante
 ↓
JpaRepository
 ↓
save()
 ↓
Hibernate
 ↓
Base de datos
```

Este ejemplo muestra por primera vez cómo una petición HTTP termina produciendo un cambio real en la base de datos.

---

## 3. Listar todos los estudiantes

### Archivo principal

`U2_03_ListarEstudiantes.java`

```java
package com.lelyliliana.unidad2.ejemplo03;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class U2_03_ListarEstudiantes {

    private final EstudianteRepository estudianteRepository;

    public U2_03_ListarEstudiantes(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db")
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }
}
```

### ¿Qué hace este ejemplo?

Este ejemplo permite consultar todos los estudiantes almacenados en la base de datos.

Se utiliza una petición HTTP:

```text
GET /estudiantes-db
```

A diferencia del ejemplo anterior, aquí no se envía información al servidor. El cliente solicita los datos existentes.

---

### Método `findAll()`

La instrucción:

```java
estudianteRepository.findAll()
```

consulta todos los registros de la entidad `Estudiante`.

Spring Data JPA proporciona este método automáticamente por medio de `JpaRepository`.

No es necesario escribir manualmente una consulta SQL como:

```sql
select * from estudiante;
```

Hibernate genera internamente la consulta correspondiente.

---

### Tipo de respuesta

El método retorna:

```java
List<Estudiante>
```

Esto significa que la respuesta puede contener:

- ningún estudiante;
- un estudiante;
- varios estudiantes.

Spring convierte automáticamente la lista de objetos Java en JSON.

Por ejemplo:

```json
[
  {
    "id": 1,
    "nombre": "Ana",
    "programa": "Ingeniería de Sistemas"
  },
  {
    "id": 2,
    "nombre": "Carlos",
    "programa": "Ingeniería Industrial"
  }
]
```

---

### Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db
```

No es necesario enviar cuerpo JSON.

La respuesta mostrará todos los registros almacenados.

---

### Probar con `curl`

```bash
curl http://localhost:8080/estudiantes-db
```

Respuesta esperada:

```json
[
  {
    "id": 1,
    "nombre": "Ana",
    "programa": "Ingeniería de Sistemas"
  }
]
```

La cantidad de elementos dependerá de los datos almacenados previamente.

---

### ¿Qué ocurre si no existen registros?

Si la tabla está vacía, la respuesta será:

```json
[]
```

Esto representa una lista vacía.

No significa que haya ocurrido un error.

---

### Flujo del ejemplo

```text
Cliente
   ↓
GET /estudiantes-db
   ↓
Controlador
   ↓
findAll()
   ↓
JpaRepository
   ↓
Hibernate
   ↓
SELECT en la base de datos
   ↓
List<Estudiante>
   ↓
JSON
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- `GET` se utiliza para consultar información;
- `findAll()` recupera todos los registros;
- el repositorio abstrae el acceso a la base de datos;
- Hibernate genera la consulta SQL;
- Spring convierte una lista de objetos Java en un arreglo JSON;
- una lista vacía se representa mediante `[]`.

---

### Idea principal del ejemplo

```text
Base de datos
      ↓
findAll()
      ↓
List<Estudiante>
      ↓
JSON
      ↓
Cliente
```

Este ejemplo completa la primera operación de lectura del CRUD.

---

## 4. Buscar un estudiante por identificador

### Archivo principal

`U2_04_BuscarEstudiantePorId.java`

```java
package com.lelyliliana.unidad2.ejemplo04;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_04_BuscarEstudiantePorId {

    private final EstudianteRepository estudianteRepository;

    public U2_04_BuscarEstudiantePorId(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/{id}")
    public ResponseEntity<Estudiante> buscarPorId(
            @PathVariable Long id) {

        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
```

### ¿Qué hace este ejemplo?

Este ejemplo permite consultar un estudiante específico a partir de su identificador.

La ruta utilizada es:

```text
GET /estudiantes-db/{id}
```

El valor ubicado en `{id}` cambia según el registro que se desea consultar.

Por ejemplo:

```text
GET /estudiantes-db/1
```

busca el estudiante cuyo identificador es `1`.

---

### Uso de `@PathVariable`

La anotación:

```java
@PathVariable Long id
```

permite obtener desde la URL el valor enviado por el cliente.

Si la petición es:

```text
/estudiantes-db/5
```

Spring asigna:

```text
5
```

a la variable:

```java
id
```

---

### Método `findById()`

La instrucción:

```java
estudianteRepository.findById(id)
```

busca un registro utilizando su llave primaria.

Este método también es proporcionado automáticamente por `JpaRepository`.

En SQL, la operación sería conceptualmente similar a:

```sql
select *
from estudiante
where id = ?;
```

---

### ¿Qué devuelve `findById()`?

`findById()` no devuelve directamente un objeto `Estudiante`.

Devuelve:

```java
Optional<Estudiante>
```

`Optional` permite representar dos posibilidades:

```text
el estudiante existe
```

o:

```text
el estudiante no existe
```

Esto ayuda a evitar el uso innecesario de valores `null`.

---

### Uso de `ResponseEntity`

El método del controlador retorna:

```java
ResponseEntity<Estudiante>
```

Esto permite controlar tanto el contenido de la respuesta como el código de estado HTTP.

Si el estudiante existe:

```java
.map(ResponseEntity::ok)
```

se devuelve:

```text
200 OK
```

junto con los datos del estudiante.

Si no existe:

```java
.orElse(ResponseEntity.notFound().build())
```

se devuelve:

```text
404 Not Found
```

sin contenido.

---

### Probar un registro existente

Con una herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db/1
```

Con `curl`:

```bash
curl -i http://localhost:8080/estudiantes-db/1
```

Respuesta esperada:

```text
HTTP/1.1 200
```

y un cuerpo similar a:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

### Probar un registro inexistente

Por ejemplo:

```bash
curl -i http://localhost:8080/estudiantes-db/99
```

Si el identificador no existe, la respuesta será:

```text
HTTP/1.1 404
```

---

### Flujo del ejemplo

```text
GET /estudiantes-db/1
        ↓
@PathVariable
        ↓
id = 1
        ↓
findById(1)
        ↓
Optional<Estudiante>
        ↓
¿existe?
   ↙          ↘
 sí            no
 ↓             ↓
200 OK       404 Not Found
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- la llave primaria permite localizar un registro específico;
- `@PathVariable` obtiene datos desde la ruta;
- `findById()` busca por identificador;
- `Optional` representa la posible ausencia de un resultado;
- `ResponseEntity` permite controlar los códigos HTTP;
- un recurso existente debe devolver `200 OK`;
- un recurso inexistente puede devolver `404 Not Found`.

---

### Idea principal del ejemplo

```text
id recibido
    ↓
findById()
    ↓
¿existe?
 ↙      ↘
sí       no
↓        ↓
200      404
```

Este ejemplo muestra cómo una API REST puede responder de manera diferente según exista o no el recurso solicitado.

---

## 5. Actualizar un estudiante con `PUT`

### Archivo principal

`U2_05_ActualizarEstudiante.java`

```java
package com.lelyliliana.unidad2.ejemplo05;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_05_ActualizarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_05_ActualizarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @PutMapping("/estudiantes-db/{id}")
    public ResponseEntity<Estudiante> actualizar(
            @PathVariable Long id,
            @RequestBody Estudiante datosActualizados) {

        return estudianteRepository.findById(id)
                .map(estudiante -> {

                    estudiante.setNombre(datosActualizados.getNombre());
                    estudiante.setPrograma(datosActualizados.getPrograma());

                    Estudiante actualizado =
                            estudianteRepository.save(estudiante);

                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
```

### ¿Qué hace este ejemplo?

Este ejemplo permite actualizar los datos de un estudiante existente.

Se utiliza el método HTTP:

```text
PUT
```

La ruta es:

```text
PUT /estudiantes-db/{id}
```

El identificador del estudiante se recibe desde la URL y los nuevos datos se envían en formato JSON dentro del cuerpo de la petición.

---

### Datos enviados

Por ejemplo:

```json
{
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

La petición podría dirigirse a:

```text
/estudiantes-db/1
```

Esto significa que se desea actualizar el estudiante con identificador `1`.

---

### Buscar antes de actualizar

Antes de modificar un registro, el controlador ejecuta:

```java
estudianteRepository.findById(id)
```

Esto permite verificar si el estudiante realmente existe.

El flujo comienza así:

```text
id recibido
    ↓
findById(id)
    ↓
¿existe?
```

Si no existe, la API devuelve:

```text
404 Not Found
```

---

### Modificar los datos

Si el estudiante existe, se actualizan sus propiedades:

```java
estudiante.setNombre(datosActualizados.getNombre());
estudiante.setPrograma(datosActualizados.getPrograma());
```

El objeto recuperado de la base de datos cambia sus valores en memoria.

---

### Guardar los cambios

Después se ejecuta:

```java
estudianteRepository.save(estudiante)
```

Aunque también utilizamos `save()` al crear registros, Spring Data JPA puede utilizar el mismo método para actualizar una entidad existente.

La diferencia es que el objeto ya posee un identificador asociado a un registro de la base de datos.

Hibernate genera una operación equivalente a:

```sql
update estudiante
set nombre = ?, programa = ?
where id = ?;
```

---

### Respuesta si la actualización es exitosa

La instrucción:

```java
ResponseEntity.ok(actualizado)
```

devuelve:

```text
200 OK
```

junto con los datos actualizados.

Por ejemplo:

```json
{
  "id": 1,
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

---

### Probar con herramienta gráfica

Configure:

```text
Método: PUT
URL: http://localhost:8080/estudiantes-db/1
```

En el cuerpo envíe:

```json
{
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

---

### Probar con `curl`

```bash
curl -i -X PUT http://localhost:8080/estudiantes-db/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana María","programa":"Ingeniería de Software"}'
```

Respuesta esperada:

```text
HTTP/1.1 200
```

y un JSON con los datos modificados.

---

### Probar un identificador inexistente

```bash
curl -i -X PUT http://localhost:8080/estudiantes-db/99 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pedro","programa":"Ingeniería Industrial"}'
```

Si el estudiante no existe:

```text
HTTP/1.1 404
```

---

### Flujo del ejemplo

```text
PUT /estudiantes-db/1
        ↓
@PathVariable
        ↓
id = 1
        ↓
@RequestBody
        ↓
nuevos datos
        ↓
findById(1)
        ↓
¿existe?
   ↙          ↘
 sí            no
 ↓             ↓
modificar      404
 ↓
save()
 ↓
UPDATE
 ↓
200 OK
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- `PUT` se utiliza para actualizar un recurso;
- el identificador permite localizar el registro;
- primero se verifica que el recurso exista;
- los nuevos valores llegan mediante `@RequestBody`;
- `save()` también puede actualizar entidades existentes;
- Hibernate genera la operación `UPDATE`;
- un recurso inexistente puede devolver `404 Not Found`.

---

### Idea principal del ejemplo

```text
buscar
  ↓
modificar
  ↓
save()
  ↓
UPDATE
  ↓
respuesta
```

Este ejemplo incorpora la operación de actualización dentro del ciclo CRUD.

---

## 6. Eliminar un estudiante con `DELETE`

### Archivo principal

`U2_06_EliminarEstudiante.java`

```java
package com.lelyliliana.unidad2.ejemplo06;

import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_06_EliminarEstudiante {

    private final EstudianteRepository estudianteRepository;

    public U2_06_EliminarEstudiante(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @DeleteMapping("/estudiantes-db/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        if (!estudianteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        estudianteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
```

### ¿Qué hace este ejemplo?

Este ejemplo permite eliminar un estudiante utilizando su identificador.

Se utiliza el método HTTP:

```text
DELETE
```

La ruta es:

```text
DELETE /estudiantes-db/{id}
```

El identificador del registro que se desea eliminar se recibe desde la URL.

---

### Verificar si el registro existe

Antes de eliminar, se utiliza:

```java
estudianteRepository.existsById(id)
```

Este método permite saber si existe un registro con el identificador recibido.

El resultado es un valor booleano:

```text
true
```

si existe, o:

```text
false
```

si no existe.

---

### ¿Por qué verificar antes de eliminar?

Si se intenta eliminar directamente un registro inexistente, la aplicación podría producir un comportamiento menos claro para quien consume la API.

Por eso primero se comprueba:

```java
if (!estudianteRepository.existsById(id)) {
    return ResponseEntity.notFound().build();
}
```

Si el registro no existe, se devuelve:

```text
404 Not Found
```

---

### Método `deleteById()`

Si el estudiante existe, se ejecuta:

```java
estudianteRepository.deleteById(id);
```

Spring Data JPA proporciona este método automáticamente.

Hibernate genera una operación equivalente a:

```sql
delete from estudiante
where id = ?;
```

---

### Código HTTP `204 No Content`

Después de eliminar correctamente se devuelve:

```java
ResponseEntity.noContent().build();
```

Esto produce:

```text
204 No Content
```

El código `204` indica que:

- la solicitud fue procesada correctamente;
- el recurso fue eliminado;
- no es necesario enviar contenido adicional en la respuesta.

---

### Probar con herramienta gráfica

Configure:

```text
Método: DELETE
URL: http://localhost:8080/estudiantes-db/1
```

No es necesario enviar cuerpo JSON.

Si el registro existe, la respuesta debe ser:

```text
204 No Content
```

---

### Probar con `curl`

```bash
curl -i -X DELETE http://localhost:8080/estudiantes-db/1
```

Respuesta esperada:

```text
HTTP/1.1 204
```

---

### Verificar que el registro fue eliminado

Después de eliminar, puede consultar nuevamente:

```bash
curl -i http://localhost:8080/estudiantes-db/1
```

Si la eliminación fue correcta, debe obtener:

```text
HTTP/1.1 404
```

---

### Probar un identificador inexistente

```bash
curl -i -X DELETE http://localhost:8080/estudiantes-db/99
```

La respuesta será:

```text
HTTP/1.1 404
```

porque no existe un recurso con ese identificador.

---

### Flujo del ejemplo

```text
DELETE /estudiantes-db/1
          ↓
@PathVariable
          ↓
id = 1
          ↓
existsById(1)
          ↓
¿existe?
   ↙            ↘
 sí              no
 ↓               ↓
deleteById()     404
 ↓
DELETE SQL
 ↓
204 No Content
```

---

### Operaciones CRUD completadas

Con este ejemplo ya se han implementado las cuatro operaciones principales del CRUD:

| Operación | Método HTTP | Método JPA principal |
|---|---|---|
| Create | `POST` | `save()` |
| Read | `GET` | `findAll()` / `findById()` |
| Update | `PUT` | `save()` |
| Delete | `DELETE` | `deleteById()` |

CRUD significa:

```text
Create
Read
Update
Delete
```

Estas operaciones representan la base de gran parte de las aplicaciones que trabajan con información persistente.

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- `DELETE` se utiliza para eliminar un recurso;
- `existsById()` permite comprobar previamente si existe;
- `deleteById()` elimina utilizando la llave primaria;
- Hibernate genera la sentencia SQL correspondiente;
- `204 No Content` indica una eliminación exitosa sin cuerpo de respuesta;
- `404 Not Found` indica que el recurso no existe.

---

### Idea principal del ejemplo

```text
buscar existencia
       ↓
¿existe?
  ↙         ↘
sí           no
↓            ↓
eliminar     404
↓
204
```

Con este ejemplo queda completado el ciclo CRUD básico utilizando Spring Data JPA.

---

## 7. Consultas derivadas con Spring Data JPA

### Archivo principal

`U2_07_BuscarPorPrograma.java`

Este ejemplo muestra cómo Spring Data JPA puede construir consultas automáticamente a partir del nombre de un método definido en el repositorio.

---

### Cambio en `EstudianteRepository`

Para este ejemplo se agregó:

```java
List<Estudiante> findByPrograma(String programa);
```

El repositorio queda conceptualmente así:

```java
public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByPrograma(String programa);
}
```

Spring Data JPA interpreta el nombre:

```text
findByPrograma
```

como una instrucción para buscar registros cuyo atributo:

```text
programa
```

coincida con el valor recibido.

No es necesario escribir una consulta SQL manual.

---

### ¿Qué significa una consulta derivada?

Una consulta derivada es una consulta que Spring construye interpretando el nombre del método.

Por ejemplo:

```java
findByPrograma(String programa)
```

puede traducirse conceptualmente a:

```sql
select *
from estudiante
where programa = ?;
```

Spring Data JPA genera la consulta automáticamente.

---

### Controlador

`U2_07_BuscarPorPrograma.java`

```java
package com.lelyliliana.unidad2.ejemplo07;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class U2_07_BuscarPorPrograma {

    private final EstudianteRepository estudianteRepository;

    public U2_07_BuscarPorPrograma(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/buscar")
    public List<Estudiante> buscarPorPrograma(
            @RequestParam String programa) {

        return estudianteRepository.findByPrograma(programa);
    }
}
```

---

### Uso de `@RequestParam`

El valor utilizado para la búsqueda se recibe mediante:

```java
@RequestParam String programa
```

Una petición puede tener esta forma:

```text
GET /estudiantes-db/buscar?programa=Ingeniería de Sistemas
```

El parámetro:

```text
programa
```

tiene como valor:

```text
Ingeniería de Sistemas
```

Ese valor se envía al método:

```java
findByPrograma(programa)
```

---

### Flujo del ejemplo

```text
GET /estudiantes-db/buscar
        ↓
?programa=Ingeniería de Sistemas
        ↓
@RequestParam
        ↓
findByPrograma(...)
        ↓
Spring Data JPA
        ↓
consulta generada
        ↓
List<Estudiante>
        ↓
JSON
```

---

### Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db/buscar
```

Agregue un parámetro de consulta:

```text
Clave: programa
Valor: Ingeniería de Sistemas
```

Si existen varios estudiantes asociados a ese programa, la respuesta puede ser:

```json
[
  {
    "id": 1,
    "nombre": "Ana",
    "programa": "Ingeniería de Sistemas"
  },
  {
    "id": 3,
    "nombre": "Laura",
    "programa": "Ingeniería de Sistemas"
  }
]
```

---

### Probar con `curl`

Cuando el valor contiene espacios o caracteres especiales, es recomendable utilizar:

```bash
curl -G http://localhost:8080/estudiantes-db/buscar \
  --data-urlencode "programa=Ingeniería de Sistemas"
```

`--data-urlencode` permite que `curl` codifique correctamente:

- espacios;
- tildes;
- caracteres especiales.

---

### ¿Qué ocurre si no hay coincidencias?

Si no existen estudiantes con el programa consultado, se devolverá:

```json
[]
```

Esto representa una lista vacía y no necesariamente un error.

---

### Ventaja de las consultas derivadas

Spring Data JPA permite construir consultas mediante nombres como:

```text
findByNombre(...)
findByPrograma(...)
findByNombreAndPrograma(...)
```

siempre que los nombres utilizados correspondan con atributos existentes de la entidad.

Esto reduce la cantidad de código necesario para consultas sencillas.

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- Spring Data JPA puede generar consultas a partir del nombre del método;
- `findByPrograma()` no fue implementado manualmente;
- `@RequestParam` permite recibir valores de búsqueda;
- el valor recibido se utiliza para filtrar información;
- una consulta puede devolver cero, uno o varios resultados;
- `curl --data-urlencode` facilita el envío de parámetros con espacios o tildes.

---

### Idea principal del ejemplo

```text
parámetro de búsqueda
        ↓
findByPrograma()
        ↓
Spring Data JPA
        ↓
consulta automática
        ↓
resultados
```

Este ejemplo muestra que Spring Data JPA puede reducir significativamente el código necesario para realizar consultas sencillas.

---

## 8. Relación entre entidades con `@ManyToOne`

### Archivos

```text
Programa.java
ProgramaRepository.java
EstudianteConPrograma.java
EstudianteConProgramaRepository.java
U2_08_RelacionManyToOne.java
```

Este ejemplo introduce una relación entre dos entidades.

La relación planteada es:

```text
Muchos estudiantes
        ↓
pertenecen a
        ↓
un programa
```

Por ejemplo:

```text
Ingeniería de Sistemas
├── Ana
├── Laura
└── Carlos
```

Esto corresponde a una relación:

```text
Many To One
```

o:

```text
Muchos a Uno
```

---

### Entidad `Programa`

La entidad `Programa` representa una tabla independiente.

```java
@Entity
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
```

Conceptualmente puede representarse como:

```text
programa
-------------------
id
nombre
```

Cada programa posee su propio identificador.

---

### Entidad `EstudianteConPrograma`

La entidad del estudiante contiene ahora una referencia a `Programa`.

La parte principal es:

```java
@ManyToOne
private Programa programa;
```

La anotación:

```java
@ManyToOne
```

indica que varios registros de `EstudianteConPrograma` pueden estar relacionados con un mismo `Programa`.

Por ejemplo:

```text
Estudiante 1 ─┐
Estudiante 2 ─┼──> Programa 1
Estudiante 3 ─┘
```

---

### ¿Qué hace Hibernate con `@ManyToOne`?

Hibernate transforma la relación entre objetos Java en una relación entre tablas.

Conceptualmente:

```text
EstudianteConPrograma.java
        ↓
@ManyToOne
        ↓
programa_id
        ↓
llave foránea
        ↓
programa.id
```

La tabla del estudiante puede terminar con una estructura similar a:

```text
estudiante_con_programa
--------------------------------
id
nombre
programa_id
```

La columna:

```text
programa_id
```

permite identificar a qué programa pertenece cada estudiante.

---

### Llave foránea

Una llave foránea permite relacionar registros de diferentes tablas.

Por ejemplo:

```text
programa
------------------------
id | nombre
1  | Ingeniería de Sistemas
```

y:

```text
estudiante_con_programa
------------------------
id | nombre | programa_id
1  | Ana    | 1
2  | Laura  | 1
```

El valor:

```text
programa_id = 1
```

indica que ambos estudiantes pertenecen al programa cuyo `id` es `1`.

---

### Repositorios

Las dos entidades cuentan con sus respectivos repositorios:

```text
ProgramaRepository
EstudianteConProgramaRepository
```

Ambos extienden:

```java
JpaRepository
```

Esto permite realizar operaciones de persistencia utilizando los métodos proporcionados por Spring Data JPA.

---

### Controlador

`U2_08_RelacionManyToOne.java`

```java
@RestController
@RequestMapping("/relaciones")
public class U2_08_RelacionManyToOne {
```

La anotación:

```java
@RequestMapping("/relaciones")
```

establece una ruta base para todos los endpoints del controlador.

Por tanto, las operaciones de este ejemplo comenzarán con:

```text
/relaciones
```

---

### Crear un programa

El endpoint:

```java
@PostMapping("/programas")
```

permite crear un programa.

La ruta completa es:

```text
POST /relaciones/programas
```

El cliente puede enviar:

```json
{
  "nombre": "Ingeniería de Sistemas"
}
```

El controlador utiliza:

```java
programaRepository.save(programa)
```

para guardar la entidad.

---

### Crear un estudiante asociado a un programa

El segundo endpoint es:

```java
@PostMapping("/programas/{programaId}/estudiantes")
```

Por ejemplo:

```text
POST /relaciones/programas/1/estudiantes
```

El valor:

```text
1
```

representa el identificador del programa.

Primero se busca el programa:

```java
programaRepository.findById(programaId)
```

Si existe, se asigna al estudiante:

```java
estudiante.setPrograma(programa);
```

Después se guarda:

```java
estudianteRepository.save(estudiante);
```

---

### Flujo de la operación

```text
programaId
    ↓
findById()
    ↓
¿existe el programa?
   ↙            ↘
 sí              no
 ↓               ↓
asignar          404
programa
 ↓
guardar estudiante
 ↓
INSERT
 ↓
relación mediante
programa_id
```

---

### Probar primero la creación del programa

Con herramienta gráfica:

```text
Método: POST
URL: http://localhost:8080/relaciones/programas
```

Cuerpo:

```json
{
  "nombre": "Ingeniería de Sistemas"
}
```

Con `curl`:

```bash
curl -X POST http://localhost:8080/relaciones/programas \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ingeniería de Sistemas"}'
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Ingeniería de Sistemas"
}
```

---

### Crear un estudiante relacionado

Después de conocer el identificador del programa:

```text
1
```

se puede crear un estudiante asociado.

Con herramienta gráfica:

```text
Método: POST
URL: http://localhost:8080/relaciones/programas/1/estudiantes
```

Cuerpo:

```json
{
  "nombre": "Ana"
}
```

Con `curl`:

```bash
curl -X POST http://localhost:8080/relaciones/programas/1/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": {
    "id": 1,
    "nombre": "Ingeniería de Sistemas"
  }
}
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- las entidades pueden estar relacionadas;
- `@ManyToOne` representa una relación muchos a uno;
- Hibernate transforma la relación entre objetos en una relación entre tablas;
- una llave foránea permite mantener la asociación;
- antes de relacionar entidades se debe verificar que el recurso asociado exista;
- la API puede representar relaciones también en formato JSON.

---

### Relación entre Java y la base de datos

```text
Java
-------------------------------
EstudianteConPrograma
    |
    | @ManyToOne
    v
Programa
```

se transforma aproximadamente en:

```text
Base de datos
--------------------------------
estudiante_con_programa
    |
    | programa_id
    v
programa
```

---

### Idea principal del ejemplo

```text
objetos relacionados
       ↓
anotaciones JPA
       ↓
Hibernate
       ↓
llave foránea
       ↓
tablas relacionadas
```

Este ejemplo permite comprender cómo JPA representa relaciones entre objetos utilizando estructuras propias de una base de datos relacional.

---

## 9. Consumir una API externa con `RestClient`

### Archivos

```text
GitHubUsuarioResponse.java
GitHubService.java
GitHubUsuarioNoEncontradoException.java
GitHubExceptionHandler.java
U2_09_ConsumirApiExterna.java
```

Este ejemplo muestra cómo una aplicación Spring Boot puede comunicarse con una API externa.

Se utiliza la API pública de GitHub para consultar información de un usuario.

El flujo general es:

```text
Cliente
   ↓
Nuestra API
   ↓
Servicio interno
   ↓
API externa de GitHub
   ↓
JSON
   ↓
DTO Java
   ↓
Respuesta al cliente
```

---

### API externa utilizada

La dirección base de GitHub es:

```text
https://api.github.com
```

Para consultar un usuario se utiliza una ruta como:

```text
/users/{usuario}
```

Por ejemplo:

```text
https://api.github.com/users/netflix
```

La API devuelve información pública en formato JSON.

---

### DTO de respuesta

`GitHubUsuarioResponse.java`

```java
public record GitHubUsuarioResponse(

        String login,

        String name,

        @JsonProperty("public_repos")
        int publicRepos,

        int followers,

        @JsonProperty("html_url")
        String htmlUrl

) {
}
```

Este `record` representa únicamente algunos campos de la respuesta recibida desde GitHub.

No es obligatorio representar todos los atributos que devuelve una API externa.

En este ejemplo se utilizan:

```text
login
name
public_repos
followers
html_url
```

---

### Uso de `@JsonProperty`

GitHub devuelve propiedades con nombres como:

```json
"public_repos"
```

Sin embargo, en Java se utiliza normalmente la convención:

```java
publicRepos
```

Por esa razón se utiliza:

```java
@JsonProperty("public_repos")
```

Esto permite relacionar:

```text
JSON
public_repos
```

con:

```text
Java
publicRepos
```

Lo mismo ocurre con:

```java
@JsonProperty("html_url")
```

---

### Servicio para consumir GitHub

`GitHubService.java`

La clase utiliza:

```java
RestClient
```

para realizar peticiones HTTP a una API externa.

La configuración principal es:

```java
this.restClient = builder
        .baseUrl("https://api.github.com")
        .build();
```

La dirección:

```text
https://api.github.com
```

se establece como URL base.

---

### Realizar la petición GET

La consulta se realiza mediante:

```java
return restClient.get()
        .uri("/users/{usuario}", usuario)
        .retrieve()
        .body(GitHubUsuarioResponse.class);
```

Puede interpretarse así:

```text
get()
→ realizar una petición GET

uri(...)
→ definir la ruta

retrieve()
→ obtener la respuesta

body(...)
→ convertir el JSON en un objeto Java
```

---

### Conversión automática de JSON

La instrucción:

```java
.body(GitHubUsuarioResponse.class)
```

indica que la respuesta debe convertirse en un objeto:

```text
GitHubUsuarioResponse
```

Spring utiliza Jackson para realizar esta conversión.

Por tanto:

```text
JSON recibido
      ↓
Jackson
      ↓
GitHubUsuarioResponse
```

---

### Controlador

`U2_09_ConsumirApiExterna.java`

```java
@GetMapping("/github/{usuario}")
public GitHubUsuarioResponse consultarUsuario(
        @PathVariable String usuario) {

    return gitHubService.buscarUsuario(usuario);
}
```

Nuestra propia API expone:

```text
GET /github/{usuario}
```

Por ejemplo:

```text
GET /github/netflix
```

El controlador no se comunica directamente con GitHub.

Utiliza:

```java
GitHubService
```

Esto permite separar responsabilidades:

```text
Controlador
→ recibe peticiones

Servicio
→ realiza la comunicación externa
```

---

### Flujo completo de la petición

```text
GET /github/netflix
        ↓
U2_09_ConsumirApiExterna
        ↓
GitHubService
        ↓
RestClient
        ↓
https://api.github.com/users/netflix
        ↓
JSON externo
        ↓
GitHubUsuarioResponse
        ↓
JSON de nuestra API
```

---

### Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/github/netflix
```

No se requiere cuerpo JSON.

La respuesta puede ser similar a:

```json
{
  "login": "Netflix",
  "name": "Netflix, Inc.",
  "public_repos": 234,
  "followers": 10123,
  "html_url": "https://github.com/Netflix"
}
```

Los valores pueden cambiar porque la información proviene de un servicio externo en tiempo real.

---

### Probar con `curl`

```bash
curl http://localhost:8080/github/netflix
```

---

### ¿Qué ocurre si el usuario no existe?

Una API externa también puede responder con errores.

Por ejemplo, GitHub devuelve:

```text
404 Not Found
```

cuando el usuario solicitado no existe.

Para manejar este caso se creó:

```text
GitHubUsuarioNoEncontradoException
```

---

### Excepción personalizada

`GitHubUsuarioNoEncontradoException.java`

```java
public class GitHubUsuarioNoEncontradoException
        extends RuntimeException {

    public GitHubUsuarioNoEncontradoException(String usuario) {
        super("No se encontró el usuario de GitHub: " + usuario);
    }
}
```

Esta excepción representa un error específico de nuestra aplicación.

---

### Capturar el error de GitHub

En `GitHubService` se utiliza:

```java
catch (HttpClientErrorException.NotFound ex) {
    throw new GitHubUsuarioNoEncontradoException(usuario);
}
```

Esto significa:

```text
GitHub responde 404
        ↓
RestClient genera una excepción
        ↓
GitHubService la captura
        ↓
se lanza una excepción propia
```

---

### Manejo centralizado del error

`GitHubExceptionHandler.java`

La clase utiliza:

```java
@RestControllerAdvice
```

y:

```java
@ExceptionHandler(GitHubUsuarioNoEncontradoException.class)
```

para transformar la excepción en una respuesta HTTP controlada.

La respuesta tendrá:

```text
404 Not Found
```

y un cuerpo similar a:

```json
{
  "error": "No se encontró el usuario de GitHub: usuario-que-no-existe"
}
```

---

### Probar un usuario inexistente

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/github/usuario-que-no-existe-123456789
```

Con `curl`:

```bash
curl -i http://localhost:8080/github/usuario-que-no-existe-123456789
```

Respuesta esperada:

```text
HTTP/1.1 404
```

y:

```json
{
  "error": "No se encontró el usuario de GitHub: usuario-que-no-existe-123456789"
}
```

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- una aplicación puede consumir servicios externos;
- `RestClient` permite realizar peticiones HTTP;
- una URL base puede reutilizarse para diferentes endpoints;
- una respuesta JSON puede convertirse en un objeto Java;
- `@JsonProperty` permite relacionar nombres JSON con atributos Java;
- el controlador puede delegar la comunicación externa a un servicio;
- los errores externos deben manejarse de forma controlada;
- una API puede transformar errores de terceros en respuestas propias.

---

### Idea principal del ejemplo

```text
nuestra API
    ↓
servicio
    ↓
RestClient
    ↓
API externa
    ↓
JSON
    ↓
DTO
    ↓
respuesta
```

Este ejemplo muestra cómo una API puede integrarse con otros sistemas y utilizar su información como parte de sus propias respuestas.

---

## 10. Persistencia con MySQL

### Archivo principal

`U2_10_PersistenciaMySQL.java`

Este ejemplo utiliza los mismos controladores, entidades y repositorios desarrollados anteriormente, pero cambia la base de datos utilizada por la aplicación.

Hasta este punto se había trabajado principalmente con H2 en memoria.

Ahora se utiliza:

```text
MySQL
```

como sistema gestor de base de datos persistente.

---

### Diferencia entre H2 y MySQL

H2 se ha utilizado para facilitar el aprendizaje y las pruebas iniciales.

En la configuración utilizada en este repositorio, H2 trabaja en memoria:

```text
H2 en memoria
    ↓
se inicia la aplicación
    ↓
se crean las tablas
    ↓
se almacenan datos
    ↓
se detiene la aplicación
    ↓
los datos desaparecen
```

MySQL funciona de forma diferente:

```text
MySQL
    ↓
se almacenan los datos
    ↓
se detiene Spring Boot
    ↓
los datos permanecen
    ↓
se inicia nuevamente Spring Boot
    ↓
los datos siguen disponibles
```

Esta diferencia permite comprender el concepto de persistencia de manera práctica.

---

### Dependencia de MySQL

En el archivo `pom.xml` de la Unidad 2 se agregó:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

Esta dependencia permite que la aplicación Java se comunique con MySQL mediante JDBC.

---

### Base de datos

Antes de ejecutar el ejemplo debe existir una base de datos llamada:

```text
lenguaje3db
```

Puede crearse desde MySQL con:

```sql
CREATE DATABASE lenguaje3db;
```

---

### Configuración mediante perfil

La configuración de MySQL se encuentra en:

```text
src/main/resources/application-mysql.properties
```

El archivo contiene:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lenguaje3db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=${MYSQL_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

spring.h2.console.enabled=false
```

---

### ¿Por qué existe un archivo separado?

La configuración principal:

```text
application.properties
```

continúa utilizando H2.

La configuración:

```text
application-mysql.properties
```

se activa únicamente cuando se utiliza el perfil:

```text
mysql
```

Esto permite trabajar con diferentes entornos sin modificar constantemente el mismo archivo.

Conceptualmente:

```text
application.properties
        ↓
H2

application-mysql.properties
        ↓
MySQL
```

---

### Perfiles de Spring

Los perfiles permiten utilizar diferentes configuraciones para una misma aplicación.

En este caso:

```text
sin perfil
→ H2

perfil mysql
→ MySQL
```

Para activar el perfil se utiliza:

```bash
-Dspring-boot.run.profiles=mysql
```

---

### Contraseña mediante variable de entorno

La contraseña de MySQL no se escribe directamente en el archivo de configuración.

Se utiliza:

```properties
spring.datasource.password=${MYSQL_PASSWORD}
```

Esto indica que Spring debe obtener la contraseña desde una variable de entorno llamada:

```text
MYSQL_PASSWORD
```

Antes de iniciar la aplicación se puede definir temporalmente:

```bash
export MYSQL_PASSWORD='contraseña'
```

> No se recomienda publicar contraseñas reales dentro de un repositorio Git.

La variable definida con `export` permanecerá disponible mientras la terminal se mantenga abierta.

---

### Ejecutar con MySQL

Desde la raíz del repositorio:

```bash
export MYSQL_PASSWORD='contraseña'
```

Luego:

```bash
mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

Si la conexión es correcta, Spring Boot iniciará utilizando MySQL en lugar de H2.

---

### ¿Qué ocurre al iniciar?

Hibernate analiza las entidades JPA y puede generar las tablas correspondientes.

Por ejemplo:

```text
Estudiante
    ↓
estudiante

Programa
    ↓
programa

EstudianteConPrograma
    ↓
estudiante_con_programa
```

También puede crear relaciones como:

```text
estudiante_con_programa.programa_id
              ↓
          programa.id
```

Esto permite observar cómo las anotaciones JPA terminan representadas físicamente en la base de datos.

---

### Propiedad `ddl-auto=update`

En el perfil MySQL se utiliza:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Esto indica que Hibernate debe adaptar la estructura de la base de datos cuando sea necesario, sin eliminar automáticamente los datos existentes al detener la aplicación.

Es diferente de:

```properties
create-drop
```

utilizado en H2 para los primeros ejemplos.

---

### Probar persistencia

Primero cree un estudiante:

```bash
curl -X POST http://localhost:8080/estudiantes-db \
  -H "Content-Type: application/json" \
  -d '{"nombre":"María","programa":"Ingeniería de Sistemas"}'
```

Por ejemplo:

```json
{
  "id": 1,
  "nombre": "María",
  "programa": "Ingeniería de Sistemas"
}
```

Luego detenga Spring Boot:

```text
Ctrl + C
```

Vuelva a iniciar la aplicación con el perfil MySQL:

```bash
mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

Finalmente consulte nuevamente:

```bash
curl http://localhost:8080/estudiantes-db/1
```

Si MySQL está funcionando correctamente, el registro seguirá disponible.

---

### Comparación práctica

| Característica | H2 en memoria | MySQL |
|---|---|---|
| Instalación externa | No necesaria | Sí |
| Datos temporales | Sí | No |
| Datos sobreviven al reinicio | No | Sí |
| Útil para aprendizaje rápido | Sí | Sí |
| Útil para persistencia real | No | Sí |
| Configuración en este repositorio | `application.properties` | `application-mysql.properties` |

---

### ¿Qué debe observar el estudiante?

Debe identificar que:

- JPA puede trabajar con diferentes motores de base de datos;
- el código Java principal no necesita cambiar para pasar de H2 a MySQL;
- la configuración determina qué base de datos utiliza la aplicación;
- los perfiles permiten separar configuraciones;
- las variables de entorno evitan escribir contraseñas directamente;
- MySQL conserva la información después de reiniciar Spring Boot;
- Hibernate puede crear y actualizar tablas a partir de las entidades JPA.

---

### Idea principal del ejemplo

```text
mismo código Java
        ↓
misma capa JPA
        ↓
Hibernate
   ↙          ↘
 H2          MySQL
temporal    persistente
```

Este ejemplo demuestra que una de las ventajas de utilizar JPA y ORM es poder mantener la lógica de la aplicación mientras cambia la tecnología utilizada para almacenar los datos.

---

## Resumen de la Unidad 2

La secuencia desarrollada en esta unidad fue:

```text
Entidad JPA
    ↓
Repositorio
    ↓
guardar
    ↓
listar
    ↓
buscar
    ↓
actualizar
    ↓
eliminar
    ↓
consultas personalizadas
    ↓
relaciones entre entidades
    ↓
consumo de API externa
    ↓
persistencia con MySQL
```

Al finalizar la unidad, el estudiante debe poder reconocer tres bloques principales:

```text
Persistencia
→ JPA
→ Hibernate
→ bases de datos

API REST
→ CRUD
→ códigos HTTP

Integración
→ RestClient
→ APIs externas
```

---

## Comandos principales

### Ejecutar con H2

```bash
mvn -pl unidad2 spring-boot:run
```

### Ejecutar con MySQL

```bash
export MYSQL_PASSWORD='contraseña'

mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

### Detener la aplicación

```text
Ctrl + C
```

---

## Recomendación de estudio

Se recomienda seguir los ejemplos en este orden:

```text
01 → 02 → 03 → 04 → 05
   → 06 → 07 → 08 → 09 → 10
```

Antes de continuar con la Unidad 3, asegúrese de comprender:

- qué es persistencia;
- qué función cumple un ORM;
- la diferencia entre JPA e Hibernate;
- qué representa una entidad;
- qué función cumple `JpaRepository`;
- cómo se construye un CRUD;
- cómo funcionan las relaciones entre entidades;
- cómo consumir una API externa;
- qué diferencia existe entre H2 y MySQL;
- cómo utilizar perfiles y variables de entorno.