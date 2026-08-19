# Lenguaje de Programación III

Repositorio de ejemplos prácticos del curso **Lenguaje de Programación III**.

Los ejemplos están desarrollados con **Java 21, Maven y Spring Boot** y se encuentran organizados por unidades y en secuencia progresiva.

El objetivo es complementar el curso con ejemplos ejecutables, actuales y documentados paso a paso.

---

## Tecnologías principales

Durante el curso se utilizan progresivamente:

- Java 21
- Maven
- Spring Boot
- Spring Web
- Jakarta Validation
- JUnit 5
- Mockito
- MockMvc
- Spring Data JPA
- Hibernate
- H2
- MySQL
- RestClient
- Spring Boot Actuator
- Micrometer
- Prometheus
- k6

---

## Organización del repositorio

```text
Curso-Lenguaje-de-Programacion-3/
│
├── README.md
├── pom.xml
│
├── unidad1/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── unidad2/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
└── unidad3/
    ├── README.md
    ├── pom.xml
    ├── src/
    └── k6/
```

Cada unidad contiene:

- un `README.md` general como índice;
- ejemplos numerados;
- un `README.md` dentro de cada ejemplo con la explicación correspondiente.

---

## Unidades

### [Unidad 1 - Estructura de una API Web](unidad1/)

Incluye ejemplos sobre:

- Spring Boot;
- API REST;
- `GET` y `POST`;
- `PathVariable`;
- `RequestParam`;
- `RequestBody`;
- DTO;
- códigos HTTP;
- validación;
- manejo de errores;
- inyección de dependencias;
- JUnit 5;
- Mockito;
- MockMvc.

---

### [Unidad 2 - Comunicación con servicios externos](unidad2/)

Incluye ejemplos sobre:

- persistencia;
- JPA;
- Hibernate;
- Spring Data JPA;
- CRUD;
- consultas derivadas;
- relaciones entre entidades;
- consumo de APIs externas;
- H2;
- MySQL.

---

### [Unidad 3 - Observabilidad y pruebas de rendimiento](unidad3/)

Incluye ejemplos sobre:

- Spring Boot Actuator;
- health checks;
- métricas;
- métricas personalizadas;
- logging;
- trazabilidad;
- Prometheus;
- pruebas de carga;
- thresholds;
- pruebas de estrés con k6.

---

## Requisitos

Para los ejemplos principales se recomienda:

```text
Java 21
Maven 3.6.3 o superior
Git
```

Para algunos ejemplos también se utilizan:

```text
MySQL 8 o superior
k6
```

Puede verificar las versiones instaladas con:

```bash
java -version
mvn -version
git --version
mysql --version
k6 version
```

---

## Ejecutar una unidad

### Unidad 1

```bash
mvn -pl unidad1 spring-boot:run
```

### Unidad 2

```bash
mvn -pl unidad2 spring-boot:run
```

### Unidad 3

```bash
mvn -pl unidad3 spring-boot:run
```

Para detener Spring Boot:

```text
Ctrl + C
```

---

## Ejecutar todas las pruebas

Desde la raíz del repositorio:

```bash
mvn test
```

Este comando compila las tres unidades y ejecuta las pruebas automatizadas disponibles.

---

## Forma recomendada de estudio

Los ejemplos están numerados:

```text
U1_01
U1_02
U1_03
...

U2_01
U2_02
...

U3_01
U3_02
...
```

Se recomienda seguir el orden numérico dentro de cada unidad.

Cada ejemplo contiene su propia documentación con:

- objetivo;
- explicación del código;
- conceptos principales;
- forma de ejecución;
- forma de prueba;
- resultado esperado;
- flujo del ejemplo.

---

## Pruebas de APIs

Los endpoints pueden probarse mediante:

- herramientas gráficas como Postman o Bruno;
- `curl` desde la terminal.

Los comandos específicos se encuentran documentados dentro de cada ejemplo.

---

## Enfoque del repositorio

Este repositorio busca que los ejemplos sean:

- pequeños;
- progresivos;
- ejecutables;
- fáciles de localizar;
- suficientemente explicados;
- cercanos a prácticas actuales de desarrollo.

Los ejemplos complementan el material académico del curso y no lo reemplazan.

---

## Autora

**Leli Liliana Díaz Izquierdo**  
Docente - Facultad de Ingenierías