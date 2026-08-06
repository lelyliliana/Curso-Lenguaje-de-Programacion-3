# Lenguaje de Programación III

Repositorio de ejemplos prácticos del curso **Lenguaje de Programación III**.

Los ejemplos están desarrollados principalmente con **Java 21, Maven y Spring Boot** y se encuentran organizados de manera progresiva por unidades.

El propósito del repositorio es complementar los contenidos del curso mediante ejemplos ejecutables y actualizados que permitan comprender cómo se aplican los conceptos en proyectos reales de desarrollo de software.

---

## Tecnologías principales

Durante el curso se trabajará progresivamente con tecnologías como:

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
- Bases de datos relacionales
- Consumo de APIs externas
- Spring Boot Actuator
- Métricas y observabilidad
- k6 para pruebas de rendimiento

No todas las tecnologías se utilizan desde el inicio. Cada una se incorpora cuando el tema de la unidad lo requiere.

---

# Organización del repositorio

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
│   └── pom.xml
│
└── unidad3/
    ├── README.md
    └── pom.xml
```

Cada unidad contiene su propio archivo `README.md` con la explicación de los ejemplos, conceptos trabajados, comandos de ejecución y pruebas.

---

# Unidad 1 - Estructura de una API Web

En esta unidad se estudian los fundamentos de construcción y prueba de una API REST.

Se trabajan progresivamente conceptos como:

- Spring Boot.
- API REST.
- Métodos HTTP.
- Endpoints.
- `GET`.
- `POST`.
- `PathVariable`.
- `RequestParam`.
- `RequestBody`.
- JSON.
- DTO.
- códigos de estado HTTP.
- `ResponseEntity`.
- validación de datos.
- manejo de excepciones.
- inyección de dependencias.
- pruebas unitarias.
- JUnit 5.
- Mockito.
- MockMvc.

Los ejemplos de esta unidad se encuentran documentados detalladamente en:

```text
unidad1/README.md
```

---

# Unidad 2 - Comunicación con servicios externos

Esta unidad estará orientada principalmente al manejo de persistencia y comunicación con otros servicios.

Se abordarán temas como:

- persistencia de datos;
- bases de datos relacionales;
- ORM;
- JPA;
- Hibernate;
- Spring Data JPA;
- operaciones CRUD;
- relaciones entre entidades;
- consumo de APIs externas;
- manejo de respuestas de servicios externos.

Los ejemplos se incorporarán progresivamente en:

```text
unidad2/
```

---

# Unidad 3 - Observabilidad y pruebas de rendimiento

Esta unidad estará orientada al análisis del comportamiento de una aplicación una vez se encuentra en funcionamiento.

Se trabajarán temas como:

- logs;
- métricas;
- health checks;
- Spring Boot Actuator;
- observabilidad;
- monitoreo;
- pruebas de carga;
- pruebas de estrés;
- análisis de rendimiento;
- k6.

Los ejemplos estarán disponibles en:

```text
unidad3/
```

---

# Requisitos

Para trabajar con los ejemplos iniciales se recomienda tener instalado:

```text
Java 21
Maven 3.6.3 o superior
Git
```

Puede verificar Java ejecutando:

```bash
java -version
```

Puede verificar Maven ejecutando:

```bash
mvn -version
```

Puede verificar Git ejecutando:

```bash
git --version
```

---

# Clonar el repositorio

Cuando el repositorio se encuentre publicado en GitHub podrá clonarse mediante:

```bash
git clone URL_DEL_REPOSITORIO
```

Luego se debe ingresar a la carpeta:

```bash
cd Curso-Lenguaje-de-Programacion-3
```

---

# Ejecutar una unidad

Por ejemplo, para ejecutar la aplicación correspondiente a la Unidad 1:

```bash
mvn -pl unidad1 spring-boot:run
```

Para detener el servidor:

```text
Ctrl + C
```

---

# Ejecutar las pruebas

Para ejecutar las pruebas automatizadas de la Unidad 1:

```bash
mvn -pl unidad1 test
```

Maven mostrará al final el número de pruebas ejecutadas y si se presentaron errores o fallos.

---

# ¿Cómo estudiar los ejemplos?

Los ejemplos se encuentran numerados:

```text
U1_01
U1_02
U1_03
...
```

La numeración indica el orden recomendado de estudio.

No se recomienda comenzar directamente por los últimos ejemplos, ya que varios conceptos dependen de los anteriores.

Dentro de cada unidad encontrará un `README.md` con explicaciones adicionales sobre:

- qué hace el código;
- qué concepto se está trabajando;
- qué hacen las anotaciones utilizadas;
- cómo ejecutar el ejemplo;
- cómo probarlo;
- cuál debe ser el resultado esperado.

---

# Enfoque del repositorio

Este repositorio busca que los ejemplos sean:

- pequeños;
- progresivos;
- ejecutables;
- fáciles de consultar;
- suficientemente explicados;
- cercanos a prácticas actuales de desarrollo.

Los ejemplos no pretenden reemplazar el material académico del curso, sino servir como apoyo práctico para comprender y experimentar con los conceptos estudiados.

---

## Autora

**Leli Liliana Díaz Izquierdo**  
Docente - Facultad de Ingenierías