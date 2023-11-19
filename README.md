
# Monolithic application template

Este proyecto proporciona un template básico basado en Spring Boot que permite construir una aplicación monolitica. Cuenta con funcionalidades de seguridad implementadas mediante Spring Security y JWT Token.

Construido en **Java 17**, **Spring Boot 3**, **Spring Security 6**
## Funcionalidades

Permite la autenticación de usuarios a través de dos endpoints:

```http
  GET /auth/login
```

```http
  GET /auth/signup
```

Además, ofrece un endpoint para obtener información del usuario basado en el token que se envía con la petición a través del header `Authorization`.

```http
  GET /user/me
```


## Utilizar la seguridad

La seguridad es gestionada mediante roles y privilegios.

- Un usuario puede tener muchos roles.
- Los roles pueden tener muchos privilegios.

La autorización se realiza a través de estos privilegios, por lo tanto los endpoints de la aplicación deberían ser securizados utilizando la anotación @PreAuthorize e indicando el privilegio que debería tener el usuario para poder acceder a dicho endpoint.

```java
  @PreAuthorize("hasAuthority('PRIVILEGIO')")
```
## Variables de entorno

Configura las siguientes variables de entorno en tu entorno de ejecución:

`issuer` - Indica a que contexto pertenece el token y se utiliza para la verificación del mismo.

`tokenSecret` - Indica una clave secreta que se utiliza para la firma del token.

`tokenExpirationMsec` - Indica la duración en milisegundos del token.
## Información de ejecución

La aplicación utiliza una base de datos en memoria por defecto (`H2`), pero puedes cambiarla editando la configuración en el archivo `application.yml` del directorio resources.

La aplicación se auto configura con un usuario

**Email:** admin@mail.com

**Contraseña:** password

**Roles:** ADMIN 

**Privilegios:** READ, WRITE, DELETE

Puedes cambiar esta inicialización editando el archivo `data.sql` del directorio resources.
## Próximas características

- Verificación de usuario a través de envío de email y token de verificación.
- Implementación de autorización a través de servicios de terceros (OAuth2).
## Autores

- [@rodribatista](https://www.github.com/rodribatista)