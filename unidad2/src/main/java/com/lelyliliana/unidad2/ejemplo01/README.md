# U2_01 - Primera entidad JPA

Este ejemplo introduce el concepto de persistencia utilizando JPA e Hibernate.

El objetivo es comprender cómo una clase Java puede representar una tabla de base de datos y cómo Spring Data JPA permite acceder a los datos sin escribir manualmente todas las operaciones SQL básicas.

---

## Archivos

```text
Estudiante.java
EstudianteRepository.java
U2_01_PrimeraEntidadJPA.java
```

---

## Clase principal

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

Como los ejemplos están distribuidos en distintos paquetes:

```text
ejemplo01
ejemplo02
ejemplo03
...
```

se utiliza:

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

para que Spring encuentre las entidades JPA y los repositorios aunque estén distribuidos en diferentes paquetes.

> Esta configuración responde a la organización pedagógica del repositorio. En un proyecto convencional, la clase principal suele ubicarse en un paquete raíz y Spring puede detectar automáticamente los componentes ubicados debajo de ese paquete.

---

## Entidad `Estudiante`

`Estudiante.java`

La anotación:

```java
@Entity
```

indica que la clase representa una entidad que puede almacenarse en una base de datos.

En términos simples:

```text
Clase Java
   ↓
@Entity
   ↓
Tabla en la base de datos
```

La anotación:

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

indica que el identificador será generado automáticamente por la base de datos.

Los atributos:

```java
private String nombre;
private String programa;
```

se convierten en columnas de la tabla.

Conceptualmente:

```text
estudiante
----------------------------
id        BIGINT
nombre    VARCHAR
programa  VARCHAR
```

---

## ¿Qué es ORM?

ORM significa:

```text
Object-Relational Mapping
```

o:

```text
Mapeo Objeto-Relacional
```

Permite relacionar objetos de Java con estructuras de una base de datos relacional.

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

Hibernate actúa como implementación de JPA y genera las instrucciones SQL necesarias.

---

## Repositorio

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

Al extender `JpaRepository`, Spring Data JPA proporciona automáticamente métodos como:

```text
save()
findAll()
findById()
existsById()
deleteById()
```

---

## Base de datos H2

Para los primeros ejemplos se utiliza H2 en memoria.

La configuración se encuentra en:

```text
src/main/resources/application.properties
```

La URL utilizada es:

```properties
spring.datasource.url=jdbc:h2:mem:lenguaje3db
```

La palabra `mem` indica que la base se mantiene en memoria.

Esto significa:

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

---

## Creación automática de tablas

La propiedad:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

permite que Hibernate cree automáticamente las tablas al iniciar la aplicación y las elimine al detenerla.

También se utiliza:

```properties
spring.jpa.show-sql=true
```

para mostrar en la terminal las instrucciones SQL generadas por Hibernate.

---

## Ejecutar con H2

Desde la raíz del repositorio:

```bash
mvn -pl unidad2 spring-boot:run
```

La aplicación estará disponible normalmente en:

```text
http://localhost:8080
```

---

## Consola H2

Puede accederse a:

```text
http://localhost:8080/h2-console
```

URL JDBC:

```text
jdbc:h2:mem:lenguaje3db
```

Usuario:

```text
sa
```

La contraseña se encuentra vacía en esta configuración de aprendizaje.

---

## Idea principal

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

JPA no reemplaza la base de datos.

JPA permite trabajar con la información mediante objetos Java, mientras Hibernate se encarga de traducir esas operaciones al modelo relacional.