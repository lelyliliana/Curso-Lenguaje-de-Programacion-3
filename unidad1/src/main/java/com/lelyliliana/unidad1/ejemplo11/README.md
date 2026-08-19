# U1_11 - Pruebas unitarias con JUnit 5

Este ejemplo introduce las pruebas unitarias utilizando JUnit 5.

Una prueba unitaria permite verificar el comportamiento de una pequeña parte del programa de forma aislada.

---

## Archivos

Código que será probado:

```text
src/main/java/com/lelyliliana/unidad1/ejemplo11/Calculadora.java
```

Prueba:

```text
src/test/java/com/lelyliliana/unidad1/ejemplo11/CalculadoraTest.java
```

---

## Clase `Calculadora`

`Calculadora.java`

```java
package com.lelyliliana.unidad1.ejemplo11;

public class Calculadora {

    public int sumar(int a, int b) {
        return a + b;
    }

    public int dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }

        return a / b;
    }
}
```

La clase contiene dos métodos:

```text
sumar()
dividir()
```

El objetivo es comprobar automáticamente que estos métodos se comportan como se espera.

---

## Clase de prueba

`CalculadoraTest.java`

```java
package com.lelyliliana.unidad1.ejemplo11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraTest {

    private final Calculadora calculadora = new Calculadora();

    @Test
    void sumarDosNumerosRetornaElResultadoCorrecto() {
        int resultado = calculadora.sumar(4, 6);

        assertEquals(10, resultado);
    }

    @Test
    void dividirDosNumerosRetornaElResultadoCorrecto() {
        int resultado = calculadora.dividir(10, 2);

        assertEquals(5, resultado);
    }

    @Test
    void dividirEntreCeroLanzaExcepcion() {
        assertThrows(
                ArithmeticException.class,
                () -> calculadora.dividir(10, 0)
        );
    }
}
```

---

## Anotación `@Test`

La anotación:

```java
@Test
```

indica que el método corresponde a una prueba que JUnit debe ejecutar.

Por ejemplo:

```java
@Test
void sumarDosNumerosRetornaElResultadoCorrecto() {
```

---

## `assertEquals`

La instrucción:

```java
assertEquals(10, resultado);
```

compara:

```text
valor esperado
vs.
valor obtenido
```

En este caso:

```text
Esperado: 10
Obtenido: resultado
```

Si ambos valores son iguales, la prueba pasa.

Si son diferentes, la prueba falla.

---

## Probar una división

La prueba:

```java
@Test
void dividirDosNumerosRetornaElResultadoCorrecto() {
    int resultado = calculadora.dividir(10, 2);

    assertEquals(5, resultado);
}
```

comprueba que:

```text
10 / 2 = 5
```

---

## `assertThrows`

También es posible verificar que una operación lance una excepción.

El método `dividir()` contiene:

```java
if (b == 0) {
    throw new ArithmeticException("No se puede dividir entre cero");
}
```

La prueba utiliza:

```java
assertThrows(
        ArithmeticException.class,
        () -> calculadora.dividir(10, 0)
);
```

Esto significa:

```text
ejecutar dividir(10, 0)
        ↓
se espera
        ↓
ArithmeticException
```

Si la excepción esperada ocurre, la prueba pasa.

---

## Ejecutar las pruebas

No es necesario iniciar el servidor Spring Boot.

Desde la raíz del repositorio:

```bash
mvn -pl unidad1 test
```

Maven compilará el código y ejecutará las pruebas automáticamente.

---

## Resultado esperado

Para este ejemplo deben ejecutarse tres pruebas:

```text
sumarDosNumerosRetornaElResultadoCorrecto
dividirDosNumerosRetornaElResultadoCorrecto
dividirEntreCeroLanzaExcepcion
```

Una salida correcta puede mostrar:

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

---

## ¿Qué significa cada resultado?

```text
Tests run
→ cantidad de pruebas ejecutadas

Failures
→ pruebas que se ejecutaron pero obtuvieron un resultado diferente al esperado

Errors
→ pruebas que no pudieron ejecutarse correctamente debido a un error

Skipped
→ pruebas que fueron omitidas
```

---

## Flujo de una prueba unitaria

```text
Preparar datos
     ↓
Ejecutar método
     ↓
Obtener resultado
     ↓
Comparar con lo esperado
     ↓
¿coincide?
  ↙       ↘
 sí        no
 ↓         ↓
pasa      falla
```

---

## ¿Por qué automatizar las pruebas?

Las pruebas unitarias permiten:

- detectar errores rápidamente;
- comprobar que un cambio no dañó funcionalidades existentes;
- documentar el comportamiento esperado del código;
- facilitar refactorizaciones;
- mejorar la calidad del software.

---

## ¿Qué debe observar el estudiante?

- JUnit 5 permite automatizar pruebas.
- `@Test` identifica una prueba.
- `assertEquals()` compara resultados.
- `assertThrows()` verifica excepciones.
- Las pruebas unitarias se ejecutan sin levantar el servidor web.
- Una prueba debe tener un resultado esperado claramente definido.

---

## Idea principal

```text
código
 ↓
prueba
 ↓
resultado esperado
vs.
resultado obtenido
 ↓
pasa o falla
```