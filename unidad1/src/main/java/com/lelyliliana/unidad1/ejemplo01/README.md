# U1_01 - Primera aplicación con Spring Boot

Este ejemplo contiene la clase principal de la aplicación y muestra cómo iniciar un proyecto Spring Boot.

---

## Archivo principal

`U1_01_PrimeraApi.java`

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

---

## ¿Qué hace esta clase?

Esta es la clase principal desde la cual se inicia Spring Boot.

La anotación:

```java
@SpringBootApplication
```

indica que esta clase contiene la configuración principal de la aplicación.

En este repositorio se utiliza:

```java
@SpringBootApplication(scanBasePackages = "com.lelyliliana.unidad1")
```

porque los ejemplos están organizados en diferentes paquetes:

```text
ejemplo01
ejemplo02
ejemplo03
...
```

De esta forma Spring puede encontrar los controladores, servicios y demás componentes ubicados dentro de toda la Unidad 1.

---

## Método `main`

La instrucción:

```java
SpringApplication.run(U1_01_PrimeraApi.class, args);
```

inicia Spring Boot.

Entre otras tareas, Spring Boot:

- crea el contexto de la aplicación;
- detecta los componentes;
- configura el servidor web;
- inicia Tomcat embebido.

---

## Ejecutar la aplicación

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 spring-boot:run
```

Si la aplicación inicia correctamente, el servidor estará disponible normalmente en:

```text
http://localhost:8080
```

---

## Detener la aplicación

En la terminal donde está ejecutándose Spring Boot:

```text
Ctrl + C
```

---

## ¿Qué debe observar el estudiante?

- Spring Boot puede iniciar una aplicación web sin instalar un servidor Tomcat por separado.
- `@SpringBootApplication` identifica la configuración principal.
- `SpringApplication.run()` inicia la aplicación.
- Los demás ejemplos de la unidad se ejecutan dentro de esta misma aplicación.

---

## Idea principal

```text
main()
  ↓
SpringApplication.run()
  ↓
Spring Boot
  ↓
Tomcat
  ↓
Aplicación disponible
en localhost:8080
```