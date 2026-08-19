# Unidad 2 - Comunicación con servicios externos

En esta unidad se estudia cómo una aplicación Spring Boot puede almacenar información de manera persistente y comunicarse con servicios externos.

Los ejemplos están organizados de forma progresiva. Se recomienda seguir el orden indicado.

---

## Objetivos de la unidad

Al finalizar esta unidad, el estudiante estará en capacidad de:

- Comprender el concepto de persistencia.
- Identificar la función de un ORM.
- Crear entidades mediante JPA.
- Utilizar `JpaRepository`.
- Realizar operaciones CRUD.
- Crear consultas derivadas con Spring Data JPA.
- Modelar relaciones entre entidades.
- Consumir APIs externas.
- Manejar errores provenientes de servicios externos.
- Diferenciar una base de datos en memoria de una base persistente.
- Conectar Spring Boot con MySQL.

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

## Ejemplos

| Ejemplo | Tema |
|---|---|
| [U2_01](src/main/java/com/lelyliliana/unidad2/ejemplo01/) | Primera entidad JPA, ORM, repositorio y H2 |
| [U2_02](src/main/java/com/lelyliliana/unidad2/ejemplo02/) | Guardar registros con `save()` |
| [U2_03](src/main/java/com/lelyliliana/unidad2/ejemplo03/) | Listar registros con `findAll()` |
| [U2_04](src/main/java/com/lelyliliana/unidad2/ejemplo04/) | Buscar por identificador |
| [U2_05](src/main/java/com/lelyliliana/unidad2/ejemplo05/) | Actualizar registros con `PUT` |
| [U2_06](src/main/java/com/lelyliliana/unidad2/ejemplo06/) | Eliminar registros con `DELETE` |
| [U2_07](src/main/java/com/lelyliliana/unidad2/ejemplo07/) | Consultas derivadas con Spring Data JPA |
| [U2_08](src/main/java/com/lelyliliana/unidad2/ejemplo08/) | Relaciones entre entidades con `@ManyToOne` |
| [U2_09](src/main/java/com/lelyliliana/unidad2/ejemplo09/) | Consumo de API externa con `RestClient` |
| [U2_10](src/main/java/com/lelyliliana/unidad2/ejemplo10/) | Persistencia con MySQL |

Cada carpeta contiene su propio `README.md` con:

- explicación del ejemplo;
- archivos involucrados;
- conceptos principales;
- flujo de ejecución;
- forma de probarlo;
- resultados esperados.

---

## Ejecución con H2

Por defecto, la Unidad 2 utiliza H2 en memoria:

```bash
mvn -pl unidad2 spring-boot:run
```

---

## Ejecución con MySQL

Para utilizar MySQL:

```bash
export MYSQL_PASSWORD='contraseña'
```

Luego:

```bash
mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

---

## Formas de probar los endpoints

Los ejemplos pueden probarse mediante:

- una herramienta gráfica para APIs, como Postman o Bruno;
- `curl` desde la terminal.

Los comandos y ejemplos específicos se encuentran dentro del `README.md` de cada ejemplo.

---

## Ruta de aprendizaje

```text
JPA
 ↓
Repositorio
 ↓
CRUD
 ↓
Consultas
 ↓
Relaciones
 ↓
APIs externas
 ↓
MySQL
```

Se recomienda estudiar los ejemplos en este orden:

```text
01 → 02 → 03 → 04 → 05
   → 06 → 07 → 08 → 09 → 10
```
