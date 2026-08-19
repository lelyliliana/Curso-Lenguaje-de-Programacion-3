# Unidad 1 - Estructura de una API Web

En esta unidad se estudian los fundamentos para construir y probar una API REST con Java y Spring Boot.

Los ejemplos están organizados de forma progresiva. Se recomienda seguir el orden indicado.

---

## Objetivos de la unidad

Al finalizar esta unidad, el estudiante estará en capacidad de:

- Comprender la estructura básica de una aplicación Spring Boot.
- Crear endpoints REST.
- Diferenciar `PathVariable`, `RequestParam` y `RequestBody`.
- Recibir datos en formato JSON.
- Utilizar DTO para representar datos de entrada.
- Trabajar con códigos de estado HTTP.
- Validar información recibida.
- Manejar errores de validación.
- Comprender la inyección de dependencias.
- Crear pruebas unitarias con JUnit 5.
- Simular dependencias con Mockito.
- Probar controladores REST con MockMvc.

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

## Ejemplos

| Ejemplo | Tema |
|---|---|
| [U1_01](src/main/java/com/lelyliliana/unidad1/ejemplo01/) | Primera aplicación con Spring Boot |
| [U1_02](src/main/java/com/lelyliliana/unidad1/ejemplo02/) | Endpoint `GET` básico |
| [U1_03](src/main/java/com/lelyliliana/unidad1/ejemplo03/) | Parámetros con `PathVariable` |
| [U1_04](src/main/java/com/lelyliliana/unidad1/ejemplo04/) | Parámetros con `RequestParam` |
| [U1_05](src/main/java/com/lelyliliana/unidad1/ejemplo05/) | `POST`, JSON y `RequestBody` |
| [U1_06](src/main/java/com/lelyliliana/unidad1/ejemplo06/) | Uso de DTO y `record` |
| [U1_07](src/main/java/com/lelyliliana/unidad1/ejemplo07/) | Códigos HTTP con `ResponseEntity` |
| [U1_08](src/main/java/com/lelyliliana/unidad1/ejemplo08/) | Validación con Jakarta Validation |
| [U1_09](src/main/java/com/lelyliliana/unidad1/ejemplo09/) | Manejo centralizado de errores |
| [U1_10](src/main/java/com/lelyliliana/unidad1/ejemplo10/) | Inyección de dependencias |
| [U1_11](src/main/java/com/lelyliliana/unidad1/ejemplo11/) | Pruebas unitarias con JUnit 5 |
| [U1_12](src/main/java/com/lelyliliana/unidad1/ejemplo12/) | Simulación de dependencias con Mockito |
| [U1_13](src/main/java/com/lelyliliana/unidad1/ejemplo13/) | Prueba de controladores con MockMvc |

Cada carpeta contiene su propio `README.md` con:

- explicación del ejemplo;
- archivos involucrados;
- conceptos principales;
- forma de probarlo;
- resultado esperado;
- flujo de ejecución.

---

## Ejecutar la Unidad 1

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 spring-boot:run
```

La aplicación estará disponible normalmente en:

```text
http://localhost:8080
```

Para detenerla:

```text
Ctrl + C
```

---

## Ejecutar las pruebas

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 test
```

---

## Formas de probar los endpoints

Los ejemplos pueden probarse mediante:

- una herramienta gráfica para APIs, como Postman o Bruno;
- `curl` desde la terminal.

Los detalles específicos se encuentran dentro del `README.md` de cada ejemplo.

---

## Ruta de aprendizaje

```text
Spring Boot
    ↓
API REST
    ↓
GET
    ↓
PathVariable / RequestParam
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
JUnit
    ↓
Mockito
    ↓
MockMvc
```

Se recomienda estudiar los ejemplos en este orden:

```text
01 → 02 → 03 → 04 → 05 → 06 → 07
   → 08 → 09 → 10 → 11 → 12 → 13
```