# U2_09 - Consumir una API externa con `RestClient`

Este ejemplo muestra cómo una aplicación Spring Boot puede comunicarse con una API externa y utilizar la información recibida dentro de su propia respuesta.

Se utiliza la API pública de GitHub para consultar información de usuarios.

---

## Archivos

```text
GitHubUsuarioResponse.java
GitHubService.java
GitHubUsuarioNoEncontradoException.java
GitHubExceptionHandler.java
U2_09_ConsumirApiExterna.java
```

---

## Flujo general

```text
Cliente
   ↓
Nuestra API
   ↓
Controlador
   ↓
Servicio
   ↓
RestClient
   ↓
API de GitHub
   ↓
JSON externo
   ↓
DTO Java
   ↓
Respuesta al cliente
```

---

## API externa utilizada

La dirección base de GitHub es:

```text
https://api.github.com
```

Para consultar un usuario se utiliza una ruta como:

```text
/users/{usuario}
```

Por ejemplo:

```text
https://api.github.com/users/netflix
```

GitHub devuelve la información en formato JSON.

---

## DTO de respuesta

`GitHubUsuarioResponse.java`

```java
public record GitHubUsuarioResponse(

        String login,

        String name,

        @JsonProperty("public_repos")
        int publicRepos,

        int followers,

        @JsonProperty("html_url")
        String htmlUrl

) {
}
```

Este `record` representa únicamente algunos campos de la respuesta de GitHub.

No es necesario modelar todos los atributos que devuelve una API externa.

En este ejemplo se utilizan:

```text
login
name
public_repos
followers
html_url
```

---

## Uso de `@JsonProperty`

GitHub devuelve propiedades como:

```json
"public_repos": 234
```

pero en Java se utiliza:

```java
publicRepos
```

Por eso se declara:

```java
@JsonProperty("public_repos")
```

Esto permite relacionar:

```text
JSON: public_repos
Java: publicRepos
```

Lo mismo ocurre con:

```java
@JsonProperty("html_url")
```

---

## Servicio para consumir GitHub

`GitHubService.java`

La clase utiliza:

```java
RestClient
```

para realizar peticiones HTTP a un servicio externo.

La configuración principal es:

```java
this.restClient = builder
        .baseUrl("https://api.github.com")
        .build();
```

La URL base se configura una sola vez.

---

## Realizar la petición

La consulta se realiza mediante:

```java
return restClient.get()
        .uri("/users/{usuario}", usuario)
        .retrieve()
        .body(GitHubUsuarioResponse.class);
```

Puede interpretarse así:

```text
get()
→ realizar una petición GET

uri(...)
→ construir la ruta

retrieve()
→ recuperar la respuesta

body(...)
→ convertir el JSON a un objeto Java
```

---

## Conversión del JSON

La instrucción:

```java
.body(GitHubUsuarioResponse.class)
```

indica que la respuesta debe convertirse en:

```text
GitHubUsuarioResponse
```

Spring utiliza Jackson para realizar esa conversión.

```text
JSON externo
     ↓
Jackson
     ↓
GitHubUsuarioResponse
```

---

## Controlador

`U2_09_ConsumirApiExterna.java`

```java
@GetMapping("/github/{usuario}")
public GitHubUsuarioResponse consultarUsuario(
        @PathVariable String usuario) {

    return gitHubService.buscarUsuario(usuario);
}
```

Nuestra API expone una ruta como:

```text
GET /github/netflix
```

El controlador delega la comunicación externa al servicio:

```text
Controlador
→ recibe la petición

GitHubService
→ se comunica con GitHub
```

Esto ayuda a mantener separadas las responsabilidades.

---

## Probar con herramienta gráfica

Configure:

```text
Método: GET
URL: http://localhost:8080/github/netflix
```

No es necesario enviar cuerpo JSON.

Una respuesta posible es:

```json
{
  "login": "Netflix",
  "name": "Netflix, Inc.",
  "public_repos": 234,
  "followers": 10123,
  "html_url": "https://github.com/Netflix"
}
```

Los valores pueden cambiar porque provienen de una API externa.

---

## Probar con `curl`

```bash
curl http://localhost:8080/github/netflix
```

---

## ¿Qué ocurre si el usuario no existe?

GitHub devuelve:

```text
404 Not Found
```

cuando el usuario solicitado no existe.

Para manejar este caso se creó:

```text
GitHubUsuarioNoEncontradoException
```

---

## Excepción personalizada

`GitHubUsuarioNoEncontradoException.java`

```java
public class GitHubUsuarioNoEncontradoException
        extends RuntimeException {

    public GitHubUsuarioNoEncontradoException(String usuario) {
        super("No se encontró el usuario de GitHub: " + usuario);
    }
}
```

Esta excepción representa un error específico de nuestra aplicación.

---

## Capturar el error externo

En `GitHubService` se utiliza:

```java
catch (HttpClientErrorException.NotFound ex) {
    throw new GitHubUsuarioNoEncontradoException(usuario);
}
```

El flujo es:

```text
GitHub responde 404
        ↓
RestClient genera excepción
        ↓
GitHubService la captura
        ↓
se lanza una excepción propia
```

---

## Manejo centralizado del error

`GitHubExceptionHandler.java`

La clase utiliza:

```java
@RestControllerAdvice
```

junto con:

```java
@ExceptionHandler(GitHubUsuarioNoEncontradoException.class)
```

para devolver una respuesta controlada.

La respuesta será:

```text
404 Not Found
```

con un cuerpo similar a:

```json
{
  "error": "No se encontró el usuario de GitHub: usuario-que-no-existe"
}
```

---

## Probar un usuario inexistente

Con herramienta gráfica:

```text
Método: GET
URL: http://localhost:8080/github/usuario-que-no-existe-123456789
```

Con `curl`:

```bash
curl -i http://localhost:8080/github/usuario-que-no-existe-123456789
```

Respuesta esperada:

```text
HTTP/1.1 404
```

y:

```json
{
  "error": "No se encontró el usuario de GitHub: usuario-que-no-existe-123456789"
}
```

---

## ¿Qué debe observar el estudiante?

- Una aplicación puede consumir APIs externas.
- `RestClient` permite realizar peticiones HTTP.
- Una respuesta JSON puede convertirse en un objeto Java.
- `@JsonProperty` ayuda a mapear nombres distintos entre JSON y Java.
- El controlador puede delegar la comunicación externa a un servicio.
- Los errores de servicios externos deben manejarse de forma controlada.
- Una API puede transformar errores externos en respuestas propias.

---

## Idea principal

```text
Nuestra API
    ↓
GitHubService
    ↓
RestClient
    ↓
GitHub
    ↓
JSON
    ↓
DTO
    ↓
Respuesta
```