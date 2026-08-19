# U1_12 - Simulación de dependencias con Mockito

Este ejemplo muestra cómo probar una clase que depende de otra sin utilizar una implementación real de esa dependencia.

Para ello se utiliza Mockito.

---

## Archivos

Código principal:

```text
RepositorioEstudiantes.java
EstudianteService.java
```

Prueba:

```text
src/test/java/com/lelyliliana/unidad1/ejemplo12/EstudianteServiceTest.java
```

---

## Interfaz `RepositorioEstudiantes`

`RepositorioEstudiantes.java`

```java
package com.lelyliliana.unidad1.ejemplo12;

public interface RepositorioEstudiantes {

    boolean existePorId(Long id);
}
```

Esta interfaz representa una dependencia del servicio.

Su responsabilidad es indicar si existe un estudiante con determinado identificador.

En un proyecto real, esta dependencia podría estar conectada a:

- una base de datos;
- un repositorio JPA;
- una API externa;
- otro sistema.

En este ejemplo no se utiliza ninguna de esas implementaciones reales.

---

## Servicio

`EstudianteService.java`

```java
package com.lelyliliana.unidad1.ejemplo12;

public class EstudianteService {

    private final RepositorioEstudiantes repositorioEstudiantes;

    public EstudianteService(RepositorioEstudiantes repositorioEstudiantes) {
        this.repositorioEstudiantes = repositorioEstudiantes;
    }

    public String consultarEstado(Long id) {

        if (repositorioEstudiantes.existePorId(id)) {
            return "El estudiante existe";
        }

        return "El estudiante no existe";
    }
}
```

---

## Inyección de la dependencia

El servicio necesita:

```java
RepositorioEstudiantes
```

y lo recibe mediante el constructor:

```java
public EstudianteService(RepositorioEstudiantes repositorioEstudiantes) {
    this.repositorioEstudiantes = repositorioEstudiantes;
}
```

Esto permite sustituir fácilmente la dependencia real por una simulada durante las pruebas.

---

## ¿Qué es un mock?

Un `mock` es un objeto simulado.

Permite imitar el comportamiento de una dependencia sin utilizar su implementación real.

En este ejemplo Mockito crea un objeto que aparenta ser:

```java
RepositorioEstudiantes
```

pero cuyo comportamiento será definido directamente dentro de la prueba.

---

## Clase de prueba

`EstudianteServiceTest.java`

```java
package com.lelyliliana.unidad1.ejemplo12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EstudianteServiceTest {

    @Test
    void retornaQueElEstudianteExiste() {

        RepositorioEstudiantes repositorio =
                mock(RepositorioEstudiantes.class);

        when(repositorio.existePorId(1L))
                .thenReturn(true);

        EstudianteService servicio =
                new EstudianteService(repositorio);

        String resultado = servicio.consultarEstado(1L);

        assertEquals("El estudiante existe", resultado);
    }

    @Test
    void retornaQueElEstudianteNoExiste() {

        RepositorioEstudiantes repositorio =
                mock(RepositorioEstudiantes.class);

        when(repositorio.existePorId(2L))
                .thenReturn(false);

        EstudianteService servicio =
                new EstudianteService(repositorio);

        String resultado = servicio.consultarEstado(2L);

        assertEquals("El estudiante no existe", resultado);
    }
}
```

---

## Crear el mock

La instrucción:

```java
mock(RepositorioEstudiantes.class)
```

crea una implementación simulada de la interfaz.

No existe una base de datos real ni una implementación concreta del repositorio.

---

## Definir el comportamiento

Mockito permite indicar cómo debe responder el mock.

Por ejemplo:

```java
when(repositorio.existePorId(1L))
        .thenReturn(true);
```

puede leerse como:

```text
cuando
repositorio.existePorId(1)

entonces devolver
true
```

Otro caso:

```java
when(repositorio.existePorId(2L))
        .thenReturn(false);
```

simula que el estudiante no existe.

---

## Crear el servicio con el mock

Después se crea:

```java
EstudianteService servicio =
        new EstudianteService(repositorio);
```

La dependencia recibida por el servicio no es un repositorio real.

Es el objeto simulado creado por Mockito.

---

## Ejecutar la lógica

La prueba ejecuta:

```java
String resultado = servicio.consultarEstado(1L);
```

El servicio llama internamente a:

```java
repositorio.existePorId(1L)
```

pero Mockito responde con el valor que configuramos previamente.

---

## Verificar el resultado

Finalmente:

```java
assertEquals("El estudiante existe", resultado);
```

comprueba que la lógica del servicio produzca el resultado esperado.

---

## ¿Por qué utilizar Mockito?

Supongamos que `RepositorioEstudiantes` dependiera realmente de una base de datos.

Sin Mockito, para probar `EstudianteService` posiblemente necesitaríamos:

```text
levantar la base de datos
        ↓
crear tablas
        ↓
insertar registros
        ↓
ejecutar la prueba
```

Con Mockito:

```text
crear mock
    ↓
definir respuesta
    ↓
probar servicio
```

La prueba se concentra exclusivamente en la lógica de `EstudianteService`.

---

## Relación con la inyección de dependencias

Este ejemplo demuestra una ventaja importante de la inyección de dependencias.

Como el servicio recibe:

```java
RepositorioEstudiantes
```

desde el exterior, durante la prueba podemos proporcionarle:

```text
un repositorio real
```

o:

```text
un mock
```

sin modificar `EstudianteService`.

---

## Ejecutar las pruebas

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 test
```

Para este ejemplo se ejecutan dos casos:

```text
el estudiante existe
el estudiante no existe
```

---

## Flujo

```text
Prueba
  ↓
Mockito crea mock
  ↓
when(...).thenReturn(...)
  ↓
mock se inyecta
en EstudianteService
  ↓
se ejecuta la lógica
  ↓
assertEquals()
```

---

## ¿Qué debe observar el estudiante?

- Mockito permite crear dependencias simuladas.
- `mock()` crea un objeto simulado.
- `when(...).thenReturn(...)` define su comportamiento.
- La prueba no necesita una base de datos real.
- La inyección de dependencias facilita sustituir implementaciones.
- Una prueba unitaria debe concentrarse en la clase que se desea evaluar.

---

## Idea principal

```text
dependencia real
      ↓
se sustituye por
      ↓
mock
      ↓
se controla su comportamiento
      ↓
se prueba la lógica aislada
```