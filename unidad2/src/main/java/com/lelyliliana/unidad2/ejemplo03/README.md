# U2_03 - Listar todos los estudiantes

Este ejemplo muestra cómo consultar todos los registros almacenados en la base de datos utilizando Spring Data JPA.

---

## Archivo principal

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

---

## ¿Qué hace este ejemplo?

El cliente realiza una petición:

```text
GET /estudiantes-db
```

El controlador ejecuta:

```java
estudianteRepository.findAll()
```

y obtiene todos los registros almacenados.

---

## Método `findAll()`

`findAll()` es proporcionado automáticamente por `JpaRepository`.

Conceptualmente, Hibernate genera una consulta similar a:

```sql
select *
from estudiante;
```

El resultado se devuelve como:

```java
List<Estudiante>
```

Spring transforma automáticamente esa lista de objetos Java en JSON.

---

## Respuesta esperada

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

Si no existen registros, la respuesta será:

```json
[]
```

Una lista vacía no representa un error.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db
```

No es necesario enviar cuerpo JSON.

---

## Probar con `curl`

```bash
curl http://localhost:8080/estudiantes-db
```

---

## Flujo

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
Base de datos
   ↓
List<Estudiante>
   ↓
JSON
```

---

## ¿Qué debe observar el estudiante?

- `GET` permite consultar información.
- `findAll()` recupera todos los registros.
- El repositorio abstrae el acceso a la base de datos.
- Hibernate genera la consulta SQL.
- Spring transforma una lista de objetos en un arreglo JSON.
- Una tabla sin registros produce una respuesta `[]`.

---

## Idea principal

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