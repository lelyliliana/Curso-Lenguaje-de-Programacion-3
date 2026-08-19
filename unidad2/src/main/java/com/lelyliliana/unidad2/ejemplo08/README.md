# U2_08 - Relación entre entidades con `@ManyToOne`

Este ejemplo muestra cómo representar una relación entre dos entidades utilizando JPA.

La relación planteada es:

```text
Muchos estudiantes
        ↓
pertenecen a
        ↓
un programa
```

---

## Archivos

```text
Programa.java
ProgramaRepository.java
EstudianteConPrograma.java
EstudianteConProgramaRepository.java
U2_08_RelacionManyToOne.java
```

---

## Entidad `Programa`

`Programa.java`

```java
@Entity
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
```

Esta entidad representa una tabla con una estructura similar a:

```text
programa
-------------------
id
nombre
```

---

## Entidad `EstudianteConPrograma`

La parte principal de la relación es:

```java
@ManyToOne
private Programa programa;
```

La anotación:

```java
@ManyToOne
```

indica que varios estudiantes pueden estar relacionados con un mismo programa.

Por ejemplo:

```text
Ana ───────┐
Laura ─────┼──> Ingeniería de Sistemas
Carlos ────┘
```

---

## ¿Qué hace Hibernate con esta relación?

Hibernate transforma la relación entre objetos Java en una relación entre tablas.

Conceptualmente:

```text
EstudianteConPrograma
        ↓
@ManyToOne
        ↓
programa_id
        ↓
llave foránea
        ↓
Programa
```

La tabla del estudiante puede quedar así:

```text
estudiante_con_programa
--------------------------------
id
nombre
programa_id
```

---

## Llave foránea

Una llave foránea relaciona registros de tablas diferentes.

Ejemplo:

```text
programa
----------------------------
id | nombre
1  | Ingeniería de Sistemas
```

y:

```text
estudiante_con_programa
----------------------------
id | nombre | programa_id
1  | Ana    | 1
2  | Laura  | 1
```

El valor:

```text
programa_id = 1
```

indica que ambos estudiantes pertenecen al programa con identificador `1`.

---

## Repositorios

Las entidades utilizan:

```text
ProgramaRepository
EstudianteConProgramaRepository
```

Ambos extienden:

```java
JpaRepository
```

y permiten trabajar con las entidades sin implementar manualmente las operaciones básicas de persistencia.

---

## Controlador

`U2_08_RelacionManyToOne.java`

La clase utiliza:

```java
@RequestMapping("/relaciones")
```

como ruta base.

Por tanto, todos los endpoints de este ejemplo comienzan con:

```text
/relaciones
```

---

## Crear un programa

El endpoint:

```java
@PostMapping("/programas")
```

produce la ruta:

```text
POST /relaciones/programas
```

El cliente puede enviar:

```json
{
  "nombre": "Ingeniería de Sistemas"
}
```

El programa se almacena mediante:

```java
programaRepository.save(programa)
```

---

## Crear un estudiante asociado a un programa

El endpoint es:

```java
@PostMapping("/programas/{programaId}/estudiantes")
```

Por ejemplo:

```text
POST /relaciones/programas/1/estudiantes
```

Primero se busca el programa:

```java
programaRepository.findById(programaId)
```

Si existe, se asigna:

```java
estudiante.setPrograma(programa);
```

y luego se guarda:

```java
estudianteRepository.save(estudiante);
```

---

## Probar la creación del programa

Con herramienta gráfica:

```text
Método: POST
URL: http://localhost:8080/relaciones/programas
```

Cuerpo:

```json
{
  "nombre": "Ingeniería de Sistemas"
}
```

Con `curl`:

```bash
curl -X POST http://localhost:8080/relaciones/programas \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ingeniería de Sistemas"}'
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Ingeniería de Sistemas"
}
```

---

## Crear un estudiante relacionado

Con herramienta gráfica:

```text
Método: POST
URL: http://localhost:8080/relaciones/programas/1/estudiantes
```

Cuerpo:

```json
{
  "nombre": "Ana"
}
```

Con `curl`:

```bash
curl -X POST http://localhost:8080/relaciones/programas/1/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'
```

Respuesta esperada:

```json
{
  "id": 1,
  "nombre": "Ana",
  "programa": {
    "id": 1,
    "nombre": "Ingeniería de Sistemas"
  }
}
```

---

## Flujo

```text
programaId
    ↓
findById()
    ↓
¿existe?
   ↙        ↘
 sí          no
 ↓           ↓
asignar      404
programa
 ↓
guardar estudiante
 ↓
programa_id
```

---

## Relación Java vs. base de datos

```text
Java
-------------------------
EstudianteConPrograma
        |
        | @ManyToOne
        v
Programa
```

se transforma en:

```text
Base de datos
-------------------------
estudiante_con_programa
        |
        | programa_id
        v
programa
```

---

## ¿Qué debe observar el estudiante?

- Las entidades pueden relacionarse entre sí.
- `@ManyToOne` representa una relación muchos a uno.
- Hibernate transforma referencias entre objetos en llaves foráneas.
- Antes de relacionar entidades conviene verificar que el recurso asociado exista.
- La relación también puede verse en la respuesta JSON.

---

## Idea principal

```text
objetos relacionados
       ↓
anotaciones JPA
       ↓
Hibernate
       ↓
llave foránea
       ↓
tablas relacionadas
```