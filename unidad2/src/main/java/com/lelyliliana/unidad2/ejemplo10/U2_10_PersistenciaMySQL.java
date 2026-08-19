package com.lelyliliana.unidad2.ejemplo10;

/**
 * Este ejemplo utiliza los mismos controladores y repositorios
 * desarrollados anteriormente, pero cambia la base de datos
 * de H2 en memoria a MySQL mediante el perfil "mysql".
 *
 * Para ejecutar la aplicación con MySQL:
 *
 * export MYSQL_PASSWORD='contraseña'
 *
 * mvn -pl unidad2 spring-boot:run \
 *   -Dspring-boot.run.profiles=mysql
 *
 * La configuración se encuentra en:
 *
 * src/main/resources/application-mysql.properties
 */
public class U2_10_PersistenciaMySQL {

    private U2_10_PersistenciaMySQL() {
        // Clase utilizada únicamente como guía del ejemplo.
    }
}