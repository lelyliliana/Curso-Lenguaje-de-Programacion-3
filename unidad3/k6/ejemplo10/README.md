# U3_10 - Prueba de estrés con k6

Este ejemplo muestra cómo aumentar progresivamente la cantidad de usuarios virtuales para observar el comportamiento de la aplicación bajo una carga creciente.

A diferencia de una prueba de carga básica, una prueba de estrés busca someter el sistema a niveles de uso cada vez mayores.

---

## Archivo principal

`U3_10_PruebaEstres.js`

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 10 },
        { duration: '10s', target: 25 },
        { duration: '10s', target: 50 },
        { duration: '10s', target: 0 },
    ],

    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<1000'],
    },
};

export default function () {

    const response = http.get(
        'http://localhost:8080/mensaje'
    );

    check(response, {
        'estado HTTP es 200': (r) => r.status === 200,
    });

    sleep(0.1);
}
```

---

## ¿Qué es una prueba de estrés?

Una prueba de estrés incrementa progresivamente la carga para observar cómo responde el sistema.

El objetivo no es únicamente medir el comportamiento normal, sino analizar qué ocurre cuando la demanda aumenta.

Puede representarse así:

```text
carga baja
   ↓
carga media
   ↓
carga alta
   ↓
reducción de carga
```

---

## Uso de `stages`

En este ejemplo no se utiliza una cantidad fija de usuarios virtuales.

Se utiliza:

```javascript
stages: [
    { duration: '10s', target: 10 },
    { duration: '10s', target: 25 },
    { duration: '10s', target: 50 },
    { duration: '10s', target: 0 },
]
```

Cada etapa modifica progresivamente la cantidad de usuarios virtuales.

---

## Primera etapa

```javascript
{ duration: '10s', target: 10 }
```

Durante los primeros 10 segundos, k6 incrementa progresivamente la carga hasta llegar a:

```text
10 usuarios virtuales
```

---

## Segunda etapa

```javascript
{ duration: '10s', target: 25 }
```

La carga aumenta hasta:

```text
25 usuarios virtuales
```

---

## Tercera etapa

```javascript
{ duration: '10s', target: 50 }
```

La carga continúa aumentando hasta:

```text
50 usuarios virtuales
```

---

## Etapa final

```javascript
{ duration: '10s', target: 0 }
```

La cantidad de usuarios virtuales disminuye progresivamente hasta llegar a:

```text
0
```

Esto permite observar también cómo responde el sistema cuando la carga disminuye.

---

## Comportamiento de la carga

La prueba puede representarse así:

```text
Usuarios virtuales

50 |                 /\
   |                /  \
25 |         ______/    \
   |        /             \
10 |_______/               \
   |                        \
 0 +-------------------------
     10s   20s   30s   40s
```

---

## Thresholds

Se utilizan:

```javascript
thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1000'],
}
```

Esto establece dos criterios.

---

## Tasa máxima de errores

```javascript
http_req_failed: ['rate<0.05']
```

significa que menos del:

```text
5 %
```

de las peticiones HTTP puede fallar.

---

## Tiempo de respuesta

```javascript
http_req_duration: ['p(95)<1000']
```

significa que:

```text
95 % de las peticiones
debe responder en menos de 1000 ms
```

es decir:

```text
menos de 1 segundo
```

---

## Uso de `sleep(0.1)`

El script utiliza:

```javascript
sleep(0.1);
```

Esto introduce una pausa de:

```text
100 ms
```

entre iteraciones de cada usuario virtual.

La pausa ayuda a mantener la carga en un nivel controlado para este ejemplo académico.

Sin una pausa, los usuarios virtuales pueden enviar peticiones tan rápido como lo permita el equipo, produciendo una cantidad extremadamente alta de solicitudes.

---

## ¿Por qué se agregó esta pausa?

Durante una prueba inicial sin `sleep()` se generaron más de un millón de peticiones en aproximadamente 40 segundos.

Aunque ese comportamiento puede ser útil para pruebas de saturación más avanzadas, no es necesario para comprender inicialmente el concepto de stress testing.

Por eso este ejemplo utiliza una carga más controlada.

---

## Antes de ejecutar

La Unidad 3 debe estar funcionando:

```bash
mvn -pl unidad3 spring-boot:run
```

Mantenga Spring Boot ejecutándose.

---

## Ejecutar la prueba

En otra terminal:

```bash
k6 run unidad3/k6/ejemplo10/U3_10_PruebaEstres.js
```

La prueba durará aproximadamente:

```text
40 segundos
```

---

## Métricas importantes

Durante el análisis se recomienda observar:

```text
http_req_failed
http_req_duration
http_reqs
iterations
vus
vus_max
p(90)
p(95)
```

---

## ¿Qué representa `vus`?

```text
vus
```

indica la cantidad de usuarios virtuales activos durante la ejecución.

---

## ¿Qué representa `vus_max`?

```text
vus_max
```

representa la cantidad máxima de usuarios virtuales que la prueba puede alcanzar.

En este ejemplo:

```text
50
```

---

## ¿Qué debe analizarse?

A medida que aumenta la cantidad de usuarios, pueden ocurrir situaciones como:

```text
aumento de tiempos de respuesta
aumento de errores
mayor uso de CPU
mayor consumo de memoria
saturación de recursos
```

Una prueba de estrés ayuda a detectar en qué momento comienzan a aparecer estos problemas.

---

## Relación con observabilidad

Mientras k6 genera carga, pueden consultarse simultáneamente métricas de Actuator.

Por ejemplo:

```text
/actuator/metrics/http.server.requests
/actuator/metrics/system.cpu.usage
/actuator/metrics/jvm.memory.used
/actuator/prometheus
```

De esta forma:

```text
k6
→ genera carga

Actuator
→ permite observar el comportamiento interno
```

Esta combinación permite relacionar el rendimiento percibido por el cliente con el estado interno del servidor.

---

## Diferencia entre prueba de carga y prueba de estrés

Prueba de carga:

```text
¿cómo responde el sistema
ante una carga esperada?
```

Prueba de estrés:

```text
¿qué ocurre cuando
aumentamos progresivamente
la carga?
```

Ambas pruebas son complementarias.

---

## Flujo

```text
k6
 ↓
aumento progresivo de VUs
 ↓
más peticiones
 ↓
Spring Boot
 ↓
medición de tiempos y errores
 ↓
thresholds
 ↓
análisis del comportamiento
```

---

## ¿Qué debe observar el estudiante?

- `stages` permite variar la cantidad de usuarios virtuales.
- Una prueba de estrés incrementa progresivamente la carga.
- El sistema puede comportarse de manera diferente bajo mayor demanda.
- `p(95)` ayuda a analizar la latencia.
- La tasa de errores permite detectar degradación.
- `sleep()` puede utilizarse para controlar la intensidad del escenario.
- k6 y Actuator pueden utilizarse conjuntamente para analizar el sistema.

---

## Idea principal

```text
aumentar carga
      ↓
observar respuesta
      ↓
medir rendimiento
      ↓
detectar degradación
      ↓
tomar decisiones
```