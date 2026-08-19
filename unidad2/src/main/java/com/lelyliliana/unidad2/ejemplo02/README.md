# U2_02 - Guardar un estudiante con `save()`

Este ejemplo muestra cómo recibir información mediante una petición HTTP `POST` y almacenarla en la base de datos utilizando Spring Data JPA.

---

## Archivo principal

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

---

## ¿Qué hace este ejemplo?

El cliente envía datos en formato JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

Spring convierte ese JSON en un objeto Java de tipo:

```java
Estudiante
```

mediante:

```java
@RequestBody Estudiante estudiante
```

Después, el controlador utiliza:

```java
estudianteRepository.save(estudiante)
```

para almacenar el objeto en la base de datos.

---

## Flujo

```text
Cliente
   ↓
POST /estudiantes-db
   ↓
JSON
   ↓
@RequestBody
   ↓
Estudiante
   ↓
EstudianteRepository
   ↓
save()
   ↓
Hibernate
   ↓
Base de datos
```

---

## Método `save()`

`save()` es proporcionado por `JpaRepository`.

No fue necesario programar manualmente una sentencia SQL.

Hibernate genera una operación equivalente a:

```sql
insert into estudiante (nombre, programa)
values (?, ?);
```

Después de guardar, el objeto obtiene el identificador generado por la base de datos.

Ejemplo de respuesta:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

## Probar con herramienta gráfica

Configure:

```text
Método: POST
URL: http://localhost:8080/estudiantes-db
```

Cuerpo JSON:

```json
{
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

## Probar con `curl`

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

## ¿Qué debe observar el estudiante?

- `POST` permite enviar información al servidor.
- `@RequestBody` transforma el JSON en un objeto Java.
- `save()` almacena la entidad.
- Hibernate genera el SQL.
- La base de datos genera el identificador.
- La API devuelve el objeto almacenado.

---

## Idea principal

```text
JSON
 ↓
Objeto Java
 ↓
save()
 ↓
Hibernate
 ↓
Base de datos
```