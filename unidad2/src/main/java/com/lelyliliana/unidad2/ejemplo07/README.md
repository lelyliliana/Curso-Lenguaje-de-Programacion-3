# U2_07 - Consultas derivadas con Spring Data JPA

Este ejemplo muestra cómo Spring Data JPA puede generar consultas automáticamente a partir del nombre de un método definido en el repositorio.

---

## Archivo principal

`U2_07_BuscarPorPrograma.java`

Para este ejemplo también se modifica:

```text
EstudianteRepository.java
```

agregando:

```java
List<Estudiante> findByPrograma(String programa);
```

---

## ¿Qué es una consulta derivada?

Spring Data JPA puede interpretar determinados nombres de métodos y construir la consulta correspondiente.

Por ejemplo:

```java
findByPrograma(String programa)
```

puede entenderse conceptualmente como:

```sql
select *
from estudiante
where programa = ?;
```

No es necesario escribir la consulta SQL manualmente.

---

## Repositorio

El repositorio queda conceptualmente así:

```java
public interface EstudianteRepository
        extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByPrograma(String programa);
}
```

La parte:

```text
findBy
```

indica que se desea realizar una búsqueda.

La parte:

```text
Programa
```

debe corresponder con un atributo existente en la entidad `Estudiante`.

---

## Controlador

```java
package com.lelyliliana.unidad2.ejemplo07;

import com.lelyliliana.unidad2.ejemplo01.Estudiante;
import com.lelyliliana.unidad2.ejemplo01.EstudianteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class U2_07_BuscarPorPrograma {

    private final EstudianteRepository estudianteRepository;

    public U2_07_BuscarPorPrograma(
            EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/estudiantes-db/buscar")
    public List<Estudiante> buscarPorPrograma(
            @RequestParam String programa) {

        return estudianteRepository.findByPrograma(programa);
    }
}
```

---

## Uso de `@RequestParam`

El valor de búsqueda se recibe mediante:

```java
@RequestParam String programa
```

Por ejemplo:

```text
GET /estudiantes-db/buscar?programa=Ingeniería de Sistemas
```

El valor:

```text
Ingeniería de Sistemas
```

se envía al método:

```java
findByPrograma(programa)
```

---

## Flujo

```text
GET /estudiantes-db/buscar
        ↓
?programa=Ingeniería de Sistemas
        ↓
@RequestParam
        ↓
findByPrograma(...)
        ↓
Spring Data JPA
        ↓
consulta generada
        ↓
List<Estudiante>
        ↓
JSON
```

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/estudiantes-db/buscar
```

Agregue un parámetro:

```text
Clave: programa
Valor: Ingeniería de Sistemas
```

Una respuesta posible es:

```json
[
  {
    "id": 1,
    "nombre": "Ana",
    "programa": "Ingeniería de Sistemas"
  },
  {
    "id": 3,
    "nombre": "Laura",
    "programa": "Ingeniería de Sistemas"
  }
]
```

---

## Probar con `curl`

Como el valor contiene espacios y tildes, puede utilizarse:

```bash
curl -G http://localhost:8080/estudiantes-db/buscar \
  --data-urlencode "programa=Ingeniería de Sistemas"
```

`--data-urlencode` permite codificar correctamente espacios y caracteres especiales.

---

## ¿Qué ocurre si no existen coincidencias?

La respuesta será:

```json
[]
```

Esto representa una lista vacía y no necesariamente un error.

---

## Otros ejemplos de consultas derivadas

Spring Data JPA permite definir métodos como:

```text
findByNombre(...)
findByPrograma(...)
findByNombreAndPrograma(...)
```

siempre que los nombres utilizados correspondan con atributos existentes de la entidad.

---

## ¿Qué debe observar el estudiante?

- Spring Data JPA puede generar consultas a partir del nombre del método.
- `findByPrograma()` no fue implementado manualmente.
- `@RequestParam` permite recibir criterios de búsqueda.
- Una consulta puede devolver cero, uno o varios registros.
- La estructura del nombre del método es importante.
- No todas las consultas requieren escribir SQL.

---

## Idea principal

```text
criterio de búsqueda
        ↓
findByPrograma()
        ↓
Spring Data JPA
        ↓
consulta automática
        ↓
resultados
```