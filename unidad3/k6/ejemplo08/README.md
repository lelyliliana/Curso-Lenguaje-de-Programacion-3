# U3_08 - Primera prueba de carga con k6

Este ejemplo introduce el uso de k6 para realizar una prueba de carga sencilla sobre un endpoint de la aplicación.

El objetivo es simular varios usuarios realizando peticiones al mismo tiempo y observar cómo responde el sistema.

---

## Archivo principal

`U3_08_PrimeraPruebaCarga.js`

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 5,
    duration: '10s',
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

## ¿Qué es k6?

k6 es una herramienta para realizar pruebas de rendimiento y carga.

Permite simular usuarios virtuales que ejecutan peticiones contra una aplicación.

En este ejemplo se utiliza para probar:

```text
GET /mensaje
```

---

## Importar el módulo HTTP

La línea:

```javascript
import http from 'k6/http';
```

permite realizar peticiones HTTP desde el script.

---

## Importar `check` y `sleep`

```javascript
import { check, sleep } from 'k6';
```

Se utilizan dos funciones:

```text
check()
→ verifica condiciones de la respuesta

sleep()
→ introduce una pausa entre iteraciones
```

---

## Usuarios virtuales

La configuración:

```javascript
vus: 5
```

indica que k6 simulará:

```text
5 usuarios virtuales
```

Un usuario virtual, o VU, representa un usuario simulado que ejecuta repetidamente el código de la prueba.

---

## Duración

La propiedad:

```javascript
duration: '10s'
```

indica que la prueba se ejecutará durante:

```text
10 segundos
```

Por tanto:

```text
5 usuarios virtuales
        ↓
durante 10 segundos
        ↓
realizando peticiones
```

---

## Realizar la petición

La instrucción:

```javascript
const response = http.get(
    'http://localhost:8080/mensaje'
);
```

realiza una petición:

```text
GET /mensaje
```

y almacena la respuesta en:

```javascript
response
```

---

## Verificar el código HTTP

Dentro de `check()` se utiliza:

```javascript
'estado HTTP es 200': (r) => r.status === 200
```

Esto comprueba que el servidor responda:

```text
200 OK
```

---

## Verificar el contenido

También se verifica:

```javascript
'respuesta contiene el texto esperado': (r) =>
    r.body.includes('Endpoint utilizado para generar métricas')
```

Esto comprueba que el contenido recibido corresponda con el esperado.

---

## Pausa entre solicitudes

La instrucción:

```javascript
sleep(1);
```

hace que cada usuario virtual espere:

```text
1 segundo
```

antes de iniciar una nueva iteración.

Esto evita generar peticiones de forma completamente continua.

---

## Antes de ejecutar la prueba

La Unidad 3 debe estar funcionando.

Desde la raíz del repositorio:

```bash
mvn -pl unidad3 spring-boot:run
```

Mantenga Spring Boot ejecutándose.

---

## Ejecutar la prueba

En otra terminal:

```bash
k6 run unidad3/k6/ejemplo08/U3_08_PrimeraPruebaCarga.js
```

---

## Resultado observado

En la prueba realizada durante el desarrollo del ejemplo se obtuvieron:

```text
5 usuarios virtuales
10 segundos
50 peticiones
0 peticiones fallidas
100 % de checks exitosos
```

Los resultados pueden variar según:

- equipo utilizado;
- procesos ejecutándose al mismo tiempo;
- sistema operativo;
- carga actual del equipo;
- versión de las herramientas.

---

## Métricas principales de k6

k6 muestra información como:

```text
checks
http_reqs
http_req_duration
http_req_failed
iterations
vus
```

---

## `checks`

Indica el resultado de las verificaciones definidas mediante:

```javascript
check()
```

Por ejemplo:

```text
checks_succeeded: 100 %
checks_failed: 0 %
```

significa que todas las comprobaciones fueron exitosas.

---

## `http_reqs`

```text
http_reqs
```

indica la cantidad total de peticiones HTTP realizadas durante la prueba.

---

## `http_req_failed`

Esta métrica indica la proporción de peticiones HTTP que fallaron.

Por ejemplo:

```text
0.00 %
```

significa que no se presentaron peticiones fallidas.

---

## `http_req_duration`

Representa el tiempo que tardaron las peticiones HTTP.

k6 muestra valores como:

```text
avg
min
med
max
p(90)
p(95)
```

---

## Promedio

```text
avg
```

representa el tiempo promedio de respuesta.

---

## Mediana

```text
med
```

representa el valor central de los tiempos observados.

La mitad de las peticiones tardó menos que ese valor y la otra mitad tardó más.

---

## Percentil 95

```text
p(95)
```

indica que:

```text
95 % de las peticiones
tardó como máximo ese tiempo
```

Por ejemplo:

```text
p(95) = 200 ms
```

significa que el 95 % de las peticiones respondió en 200 ms o menos.

Los percentiles ayudan a observar comportamientos que un promedio puede ocultar.

---

## Flujo

```text
k6
 ↓
5 usuarios virtuales
 ↓
GET /mensaje
 ↓
Spring Boot
 ↓
respuesta
 ↓
check()
 ↓
métricas de rendimiento
```

---

## ¿Qué debe observar el estudiante?

- k6 permite simular múltiples usuarios.
- `vus` define usuarios virtuales.
- `duration` define cuánto dura la prueba.
- `http.get()` realiza una petición HTTP.
- `check()` comprueba condiciones funcionales.
- `sleep()` controla la frecuencia de las iteraciones.
- k6 mide tiempos, errores y cantidad de peticiones.
- Promedio y percentiles representan aspectos diferentes del rendimiento.

---

## Idea principal

```text
usuarios virtuales
       ↓
peticiones
       ↓
aplicación
       ↓
respuestas
       ↓
medición
       ↓
análisis de rendimiento
```