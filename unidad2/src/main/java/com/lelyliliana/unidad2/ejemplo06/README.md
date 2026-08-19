# U2_06 - Eliminar un estudiante con `DELETE`

Este ejemplo muestra cómo eliminar un estudiante utilizando su identificador y una petición HTTP `DELETE`.

---

## Archivo principal

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

---

## ¿Qué hace este ejemplo?

El cliente envía una petición como:

```text
DELETE /estudiantes-db/1
```

El valor:

```text
1
```

representa el identificador del estudiante que se desea eliminar.

---

## Verificar si el registro existe

Antes de eliminar, se ejecuta:

```java
estudianteRepository.existsById(id)
```

Este método devuelve:

```text
true
```

si el registro existe, o:

```text
false
```

si no existe.

---

## ¿Por qué comprobar primero?

Si el estudiante no existe, la API responde:

```text
404 Not Found
```

mediante:

```java
return ResponseEntity.notFound().build();
```

Esto permite dar una respuesta clara al cliente.

---

## Método `deleteById()`

Si el registro existe, se ejecuta:

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

## Código `204 No Content`

Después de eliminar correctamente, el controlador devuelve:

```java
ResponseEntity.noContent().build();
```

Esto produce:

```text
204 No Content
```

El código `204` indica que:

- la operación fue exitosa;
- el recurso fue eliminado;
- no es necesario devolver un cuerpo en la respuesta.

---

## Probar con herramienta gráfica

Configure:

```text
Método: DELETE
URL: http://localhost:8080/estudiantes-db/1
```

No es necesario enviar cuerpo JSON.

Respuesta esperada:

```text
204 No Content
```

---

## Probar con `curl`

```bash
curl -i -X DELETE http://localhost:8080/estudiantes-db/1
```

Respuesta esperada:

```text
HTTP/1.1 204
```

---

## Verificar la eliminación

Después de eliminar, consulte nuevamente:

```bash
curl -i http://localhost:8080/estudiantes-db/1
```

Si el registro fue eliminado correctamente, debe obtener:

```text
HTTP/1.1 404
```

---

## Probar un identificador inexistente

```bash
curl -i -X DELETE http://localhost:8080/estudiantes-db/99
```

Respuesta esperada:

```text
HTTP/1.1 404
```

---

## CRUD completado

Con este ejemplo ya se han trabajado las cuatro operaciones principales:

| Operación | Método HTTP | Método JPA |
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

---

## Flujo

```text
DELETE /estudiantes-db/1
          ↓
@PathVariable
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

## ¿Qué debe observar el estudiante?

- `DELETE` se utiliza para eliminar recursos.
- `existsById()` permite comprobar si existe el registro.
- `deleteById()` elimina por llave primaria.
- Hibernate genera la sentencia SQL.
- `204 No Content` representa una eliminación exitosa sin cuerpo.
- `404 Not Found` indica que el recurso no existe.

---

## Idea principal

```text
comprobar existencia
        ↓
¿existe?
   ↙        ↘
 sí          no
 ↓           ↓
eliminar     404
 ↓
204
```