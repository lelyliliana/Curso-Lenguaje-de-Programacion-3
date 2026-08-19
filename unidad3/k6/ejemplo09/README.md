# U3_09 - Prueba de carga con `thresholds`

Este ejemplo muestra cómo definir criterios automáticos de aceptación para una prueba de rendimiento con k6.

En lugar de limitarse a observar métricas, k6 puede evaluar si la aplicación cumple condiciones mínimas previamente definidas.

---

## Archivo principal

`U3_09_PruebaConThresholds.js`

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '15s',

    thresholds: {
        http_req_failed: ['rate<0.01'],
        http_req_duration: ['p(95)<500'],
        checks: ['rate>0.99'],
    },
};

export default function () {

    const response = http.get(
        'http://localhost:8080/mensaje'
    );

    check(response, {
        'estado HTTP es 200': (r) => r.status === 200,
        'respuesta contiene el texto esperado': (r) =>
            r.body.includes('Endpoint utilizado para generar métricas'),
    });

    sleep(1);
}
```

---

## ¿Qué cambia respecto al ejemplo anterior?

En el ejemplo anterior se medía el comportamiento de la aplicación.

En este ejemplo además se definen reglas que deben cumplirse:

```text
medir
+
evaluar
```

Esto permite convertir requisitos de rendimiento en condiciones automáticas.

---

## Configuración de carga

La prueba utiliza:

```javascript
vus: 10
```

y:

```javascript
duration: '15s'
```

Por tanto:

```text
10 usuarios virtuales
        ↓
durante 15 segundos
```

---

## ¿Qué son los `thresholds`?

Los `thresholds` son umbrales que permiten definir criterios de aceptación.

En este ejemplo:

```javascript
thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
    checks: ['rate>0.99'],
}
```

se establecen tres condiciones.

---

## Umbral de errores HTTP

```javascript
http_req_failed: ['rate<0.01']
```

significa que la tasa de peticiones HTTP fallidas debe ser menor al:

```text
1 %
```

En términos sencillos:

```text
menos de 1 petición fallida
por cada 100 peticiones
```

---

## Umbral de tiempo de respuesta

```javascript
http_req_duration: ['p(95)<500']
```

significa que el percentil 95 debe ser menor de:

```text
500 ms
```

Es decir:

```text
al menos 95 % de las peticiones
debe responder en menos de 500 ms
```

---

## Umbral de checks

```javascript
checks: ['rate>0.99']
```

significa que más del:

```text
99 %
```

de las verificaciones definidas con `check()` deben ser exitosas.

---

## Antes de ejecutar

La Unidad 3 debe estar funcionando:

```bash
mvn -pl unidad3 spring-boot:run
```

---

## Ejecutar la prueba

En otra terminal:

```bash
k6 run unidad3/k6/ejemplo09/U3_09_PruebaConThresholds.js
```

---

## Resultado observado

Durante el desarrollo del ejemplo se obtuvo:

```text
checks
→ 100 %

p(95)
→ 13.79 ms

errores HTTP
→ 0 %
```

Los tres umbrales se cumplieron.

k6 mostró:

```text
✓ checks
✓ http_req_duration
✓ http_req_failed
```

Los valores pueden variar según el equipo y las condiciones de ejecución.

---

## Interpretar los resultados

En este caso:

```text
checks > 99 %
→ cumplido

p(95) < 500 ms
→ cumplido

errores < 1 %
→ cumplido
```

Por tanto, la prueba se considera exitosa según los criterios definidos.

---

## ¿Qué ocurre si un threshold falla?

Si alguna condición no se cumple, k6 marca el threshold como fallido.

Por ejemplo:

```text
✗ p(95)<500
```

indicaría que el tiempo de respuesta del 95 % de las peticiones superó el límite permitido.

Esto permite que la prueba no dependa únicamente de una interpretación manual.

---

## Diferencia entre `check` y `threshold`

`check()` verifica condiciones sobre respuestas individuales.

Por ejemplo:

```text
¿esta petición devolvió 200?
```

Un `threshold` evalúa el comportamiento agregado de toda la prueba.

Por ejemplo:

```text
¿menos del 1 % de las peticiones fallaron?
```

Puede verse así:

```text
check
→ analiza respuestas individuales

threshold
→ analiza el resultado general
```

---

## Flujo

```text
k6
 ↓
10 usuarios virtuales
 ↓
peticiones
 ↓
checks
 ↓
métricas
 ↓
thresholds
 ↓
¿cumple los criterios?
   ↙          ↘
 sí            no
 ↓             ↓
éxito         fallo
```

---

## ¿Por qué utilizar thresholds?

Permiten:

- definir requisitos de rendimiento;
- automatizar criterios de aceptación;
- detectar degradaciones;
- comparar versiones de una aplicación;
- integrar pruebas de rendimiento en procesos automatizados;
- reducir la interpretación subjetiva de resultados.

---

## ¿Qué debe observar el estudiante?

- Los thresholds convierten métricas en criterios de aceptación.
- `rate<0.01` limita la tasa de errores.
- `p(95)<500` establece un límite de latencia.
- `rate>0.99` exige una alta proporción de checks exitosos.
- `check()` y `threshold` cumplen funciones diferentes.
- Una prueba de rendimiento puede aprobar o fallar automáticamente.

---

## Idea principal

```text
medir
 ↓
definir criterios
 ↓
comparar resultados
 ↓
aprobar o fallar
```