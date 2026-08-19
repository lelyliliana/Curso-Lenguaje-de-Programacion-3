# Unidad 3 - Observabilidad y pruebas de rendimiento

En esta unidad se estudia cómo observar el comportamiento interno de una aplicación y cómo evaluar su rendimiento bajo diferentes niveles de carga.

Los ejemplos están organizados de forma progresiva y combinan herramientas de observabilidad de Spring Boot con pruebas de rendimiento utilizando k6.

---

## Objetivos de la unidad

Al finalizar esta unidad, el estudiante estará en capacidad de:

- Comprender el concepto de observabilidad.
- Consultar el estado de una aplicación con Spring Boot Actuator.
- Analizar métricas generadas automáticamente.
- Crear métricas personalizadas con Micrometer.
- Registrar eventos mediante logs.
- Crear indicadores de salud personalizados.
- Comprender los conceptos de `traceId` y `spanId`.
- Exponer métricas en formato Prometheus.
- Realizar pruebas de carga con k6.
- Definir criterios de aceptación mediante `thresholds`.
- Ejecutar pruebas de estrés con carga progresiva.
- Relacionar métricas internas con resultados de rendimiento.

---

## Tecnologías utilizadas

- Java 21
- Maven
- Spring Boot 3.5
- Spring Boot Actuator
- Micrometer
- Micrometer Tracing
- Brave
- Prometheus Registry
- SLF4J
- k6

---

## Ejemplos de observabilidad

| Ejemplo | Tema |
|---|---|
| [U3_01](src/main/java/com/lelyliliana/unidad3/ejemplo01/) | Observabilidad básica con Actuator |
| [U3_02](src/main/java/com/lelyliliana/unidad3/ejemplo02/) | Métricas de peticiones HTTP |
| [U3_03](src/main/java/com/lelyliliana/unidad3/ejemplo03/) | Métrica personalizada con Micrometer |
| [U3_04](src/main/java/com/lelyliliana/unidad3/ejemplo04/) | Logging con SLF4J |
| [U3_05](src/main/java/com/lelyliliana/unidad3/ejemplo05/) | Health Indicator personalizado |
| [U3_06](src/main/java/com/lelyliliana/unidad3/ejemplo06/) | Trazabilidad con `traceId` y `spanId` |
| [U3_07](src/main/java/com/lelyliliana/unidad3/ejemplo07/) | Exportación de métricas para Prometheus |

---

## Ejemplos de pruebas de rendimiento con k6

| Ejemplo | Tema |
|---|---|
| [U3_08](k6/ejemplo08/) | Primera prueba de carga |
| [U3_09](k6/ejemplo09/) | Prueba con `thresholds` |
| [U3_10](k6/ejemplo10/) | Prueba de estrés con carga progresiva |

Cada carpeta contiene su propio `README.md` con:

- explicación del ejemplo;
- conceptos principales;
- archivos involucrados;
- forma de ejecutarlo;
- resultados esperados;
- interpretación de métricas.

---

## Ejecutar la Unidad 3

Desde la raíz del repositorio:

```bash
mvn -pl unidad3 spring-boot:run
```

La aplicación estará disponible normalmente en:

```text
http://localhost:8080
```

Para detenerla:

```text
Ctrl + C
```

---

## Endpoints principales de Actuator

La configuración actual expone:

```text
/actuator/health
/actuator/info
/actuator/metrics
/actuator/prometheus
```

---

## Ejecutar pruebas con k6

Por ejemplo:

```bash
k6 run unidad3/k6/ejemplo08/U3_08_PrimeraPruebaCarga.js
```

Los demás comandos se encuentran documentados dentro de cada ejemplo.

---

## Ruta de aprendizaje

```text
Observabilidad
      ↓
Health
      ↓
Métricas
      ↓
Métricas personalizadas
      ↓
Logs
      ↓
Health personalizado
      ↓
Trazas
      ↓
Prometheus
      ↓
Pruebas de carga
      ↓
Thresholds
      ↓
Pruebas de estrés
```

Se recomienda estudiar los ejemplos en este orden:

```text
01 → 02 → 03 → 04 → 05
   → 06 → 07 → 08 → 09 → 10
```

---

## Relación entre observabilidad y rendimiento

La Unidad 3 busca relacionar dos perspectivas:

```text
Desde dentro de la aplicación
→ Actuator
→ métricas
→ logs
→ trazas

Desde fuera de la aplicación
→ k6
→ tiempos
→ errores
→ carga
```

Al combinar ambas perspectivas se obtiene una visión más completa del comportamiento del sistema.