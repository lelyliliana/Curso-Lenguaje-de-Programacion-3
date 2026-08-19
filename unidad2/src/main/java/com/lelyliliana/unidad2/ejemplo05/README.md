# U2_05 - Actualizar un estudiante con `PUT`

Este ejemplo muestra cómo actualizar los datos de un estudiante existente utilizando una petición HTTP `PUT`.

---

## Archivo principal

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

---

## ¿Qué hace este ejemplo?

El cliente envía:

```text
PUT /estudiantes-db/{id}
```

junto con los nuevos datos del estudiante en formato JSON.

Por ejemplo:

```text
PUT /estudiantes-db/1
```

con:

```json
{
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

---

## Buscar antes de actualizar

Primero se ejecuta:

```java
estudianteRepository.findById(id)
```

Esto permite comprobar que el estudiante existe antes de modificarlo.

Si no existe, se devuelve:

```text
404 Not Found
```

---

## Modificar los datos

Si el registro existe, se actualizan sus propiedades:

```java
estudiante.setNombre(datosActualizados.getNombre());
estudiante.setPrograma(datosActualizados.getPrograma());
```

---

## Guardar los cambios

Luego se ejecuta:

```java
estudianteRepository.save(estudiante)
```

Aunque `save()` también se utiliza para crear registros, puede actualizar una entidad existente cuando esta ya tiene un identificador asociado.

Hibernate genera una operación equivalente a:

```sql
update estudiante
set nombre = ?, programa = ?
where id = ?;
```

---

## Respuesta exitosa

Si la actualización se realiza correctamente:

```java
ResponseEntity.ok(actualizado)
```

devuelve:

```text
200 OK
```

junto con los datos actualizados.

Ejemplo:

```json
{
  "id": 1,
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

---

## Probar con herramienta gráfica

Configure:

```text
Método: PUT
URL: http://localhost:8080/estudiantes-db/1
```

Cuerpo JSON:

```json
{
  "nombre": "Ana María",
  "programa": "Ingeniería de Software"
}
```

---

## Probar con `curl`

```bash
curl -i -X PUT http://localhost:8080/estudiantes-db/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana María","programa":"Ingeniería de Software"}'
```

---

## Probar un identificador inexistente

```bash
curl -i -X PUT http://localhost:8080/estudiantes-db/99 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pedro","programa":"Ingeniería Industrial"}'
```

Respuesta esperada:

```text
HTTP/1.1 404
```

---

## Flujo

```text
PUT /estudiantes-db/1
        ↓
@PathVariable
        ↓
@RequestBody
        ↓
findById()
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

## ¿Qué debe observar el estudiante?

- `PUT` se utiliza para actualizar recursos.
- `@PathVariable` identifica el registro.
- `@RequestBody` contiene los nuevos datos.
- `findById()` comprueba que exista el recurso.
- `save()` puede actualizar una entidad existente.
- Hibernate genera la sentencia `UPDATE`.
- Un recurso inexistente devuelve `404 Not Found`.

---

## Idea principal

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