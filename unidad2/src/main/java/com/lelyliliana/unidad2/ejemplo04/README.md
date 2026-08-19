# U2_04 - Buscar un estudiante por identificador

Este ejemplo muestra cómo consultar un registro específico utilizando su identificador.

---

## Archivo principal

`U2_04_BuscarEstudiantePorId.java`

```java
package com.lelyliliana.unidad2.ejemplo04;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class U2_04_BuscarEstudiantePorId {

    private final EstudianteRepository estudianteRepository;

    public U2_04_BuscarEstudiantePorId(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/{id}")
    public ResponseEntity<Estudiante> buscarPorId(
            @PathVariable Long id) {

        return estudianteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
```

---

## ¿Qué hace este ejemplo?

El cliente realiza una petición como:

```text
GET /estudiantes-db/1
```

El valor:

```text
1
```

representa el identificador del estudiante que se desea consultar.

---

## Uso de `@PathVariable`

La anotación:

```java
@PathVariable Long id
```

permite obtener el valor enviado dentro de la propia URL.

Por ejemplo:

```text
/estudiantes-db/5
```

hace que:

```java
id = 5
```

---

## Método `findById()`

El repositorio utiliza:

```java
estudianteRepository.findById(id)
```

para buscar un registro mediante su llave primaria.

Conceptualmente, Hibernate genera una consulta similar a:

```sql
select *
from estudiante
where id = ?;
```

---

## Uso de `Optional`

`findById()` devuelve:

```java
Optional<Estudiante>
```

Esto permite representar dos posibilidades:

```text
el estudiante existe
```

o:

```text
el estudiante no existe
```

De esta forma se evita trabajar directamente con valores `null`.

---

## Uso de `ResponseEntity`

El controlador retorna:

```java
ResponseEntity<Estudiante>
```

Esto permite controlar el código de estado HTTP.

Si el estudiante existe:

```java
.map(ResponseEntity::ok)
```

se devuelve:

```text
200 OK
```

junto con el estudiante.

Si no existe:

```java
.orElse(ResponseEntity.notFound().build())
```

se devuelve:

```text
404 Not Found
```

---

## Probar un registro existente

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db/1
```

Con `curl`:

```bash
curl -i http://localhost:8080/estudiantes-db/1
```

Respuesta esperada:

```text
HTTP/1.1 200
```

y un cuerpo similar a:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": "Ingeniería de Sistemas"
}
```

---

## Probar un registro inexistente

```bash
curl -i http://localhost:8080/estudiantes-db/99
```

Respuesta esperada:

```text
HTTP/1.1 404
```

---

## Flujo

```text
GET /estudiantes-db/1
        ↓
@PathVariable
        ↓
id = 1
        ↓
findById(1)
        ↓
Optional<Estudiante>
        ↓
¿existe?
   ↙          ↘
 sí            no
 ↓             ↓
200 OK       404 Not Found
```

---

## ¿Qué debe observar el estudiante?

- `@PathVariable` permite recibir valores desde la ruta.
- `findById()` busca mediante la llave primaria.
- `Optional` representa la posible ausencia del resultado.
- `ResponseEntity` permite controlar el código HTTP.
- Un recurso existente devuelve `200 OK`.
- Un recurso inexistente devuelve `404 Not Found`.

---

## Idea principal

```text
id recibido
    ↓
findById()
    ↓
¿existe?
 ↙      ↘
sí       no
↓        ↓
200      404
```