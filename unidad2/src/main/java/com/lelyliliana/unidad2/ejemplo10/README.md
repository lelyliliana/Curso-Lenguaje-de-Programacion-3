# U2_10 - Persistencia con MySQL

Este ejemplo muestra cómo utilizar MySQL como base de datos persistente sin modificar la lógica principal de la aplicación.

Hasta este punto se había trabajado con H2 en memoria. Ahora se cambia únicamente la configuración para utilizar MySQL.

---

## Archivo principal

`U2_10_PersistenciaMySQL.java`

Este archivo funciona como guía del ejemplo. No duplica controladores ni repositorios porque se reutilizan los desarrollados en los ejemplos anteriores.

---

## Diferencia entre H2 y MySQL

### H2 en memoria

```text
se inicia Spring Boot
        ↓
se crea la base
        ↓
se almacenan datos
        ↓
se detiene Spring Boot
        ↓
los datos desaparecen
```

### MySQL

```text
se almacenan datos
        ↓
se detiene Spring Boot
        ↓
los datos permanecen
        ↓
se inicia nuevamente
        ↓
los datos siguen disponibles
```

La diferencia principal es que MySQL permite mantener la información después de reiniciar la aplicación.

---

## Dependencia necesaria

En `unidad2/pom.xml` se utiliza:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

Esta dependencia permite la comunicación entre Java y MySQL.

---

## Crear la base de datos

Antes de ejecutar el ejemplo debe existir:

```text
lenguaje3db
```

Puede crearse desde MySQL con:

```sql
CREATE DATABASE lenguaje3db;
```

---

## Configuración del perfil MySQL

La configuración se encuentra en:

```text
src/main/resources/application-mysql.properties
```

Contenido:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lenguaje3db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=${MYSQL_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

spring.h2.console.enabled=false
```

---

## ¿Por qué usar un perfil?

La configuración por defecto continúa utilizando H2:

```text
application.properties
→ H2
```

Mientras que:

```text
application-mysql.properties
→ MySQL
```

se utiliza únicamente cuando se activa el perfil:

```text
mysql
```

Esto permite utilizar diferentes bases de datos sin modificar el código Java.

---

## Variable de entorno para la contraseña

La contraseña no se escribe directamente en el archivo.

Se utiliza:

```properties
spring.datasource.password=${MYSQL_PASSWORD}
```

Antes de iniciar la aplicación debe definirse:

```bash
export MYSQL_PASSWORD='contraseña'
```

> No se recomienda guardar contraseñas reales dentro del repositorio.

---

## Ejecutar con MySQL

Desde la raíz del repositorio:

```bash
export MYSQL_PASSWORD='contraseña'
```

Luego:

```bash
mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

Si la conexión es correcta, Spring Boot utilizará MySQL.

---

## ¿Qué hace Hibernate?

Al iniciar, Hibernate analiza las entidades JPA y puede crear las tablas necesarias.

Por ejemplo:

```text
Estudiante
    ↓
estudiante

Programa
    ↓
programa

EstudianteConPrograma
    ↓
estudiante_con_programa
```

También puede generar relaciones como:

```text
estudiante_con_programa.programa_id
                ↓
            programa.id
```

---

## Propiedad `ddl-auto=update`

Se utiliza:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Esto permite actualizar la estructura de la base de datos sin eliminar automáticamente los datos existentes.

Es diferente de la configuración H2:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

---

## Probar la persistencia

Primero cree un estudiante:

```bash
curl -X POST http://localhost:8080/estudiantes-db \
  -H "Content-Type: application/json" \
  -d '{"nombre":"María","programa":"Ingeniería de Sistemas"}'
```

Respuesta posible:

```json
{
  "id": 1,
  "nombre": "María",
  "programa": "Ingeniería de Sistemas"
}
```

Detenga Spring Boot:

```text
Ctrl + C
```

Vuelva a iniciar con MySQL:

```bash
mvn -pl unidad2 spring-boot:run \
  -Dspring-boot.run.profiles=mysql
```

Luego consulte:

```bash
curl http://localhost:8080/estudiantes-db/1
```

Si el registro sigue disponible, se ha comprobado la persistencia de los datos.

---

## Comparación

| Característica | H2 en memoria | MySQL |
|---|---|---|
| Instalación externa | No | Sí |
| Datos temporales | Sí | No |
| Conserva datos al reiniciar | No | Sí |
| Útil para pruebas rápidas | Sí | Sí |
| Útil para persistencia real | No | Sí |

---

## ¿Qué debe observar el estudiante?

- JPA puede trabajar con diferentes motores de base de datos.
- El código Java principal no cambia.
- La configuración determina qué base se utiliza.
- Los perfiles permiten separar entornos.
- Las variables de entorno evitan publicar contraseñas.
- MySQL conserva la información después de reiniciar la aplicación.
- Hibernate puede generar tablas a partir de las entidades JPA.

---

## Idea principal

```text
mismo código Java
        ↓
      JPA
        ↓
    Hibernate
    ↙       ↘
   H2      MySQL
temporal  persistente
```