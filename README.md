# Ecosistema 💻

## Descripción
Ecosistema es una plataforma web diseñada para conectar proveedores de servicios sustentables con diversos consumidores, tales como empresas, organizaciones y usuarios. El objetivo principal de la plataforma es promover la sostenibilidad y la colaboración entre las partes interesadas para crear un futuro más verde y responsable.

![Ecosistema](https://res.cloudinary.com/dd8pefa3c/image/upload/v1731644370/ecosistema-java_lzhbdd.png) 

## Desarrollo
La aplicación está construida con Spring Boot y utiliza MariaDB como base de datos. La autenticación de los usuarios se realiza mediante JWT y Google OAuth2, permitiendo que los usuarios inicien sesión con sus cuentas de Google. Los roles de usuario están definidos de la siguiente manera:
- Administrador: Acceso completo a todas las funcionalidades de la plataforma.
- Proveedor: Acceso limitado a funcionalidades relacionadas con la gestión de productos y servicios ofrecidos.
- Usuario: Acceso a la plataforma para interactuar con los productos y servicios disponibles.
  
La plataforma incluye:
- API RESTful para interactuar con diferentes servicios.
- API de geolocalización para obtener y mostrar datos basados en la ubicación del usuario.
- Chatbot para interactuar automáticamente con los usuarios.
- Envío de correos electrónicos automatizados para notificaciones.

## Equipo Backend 💻

| Nombre               | LinkedIn                                           |
|----------------------|----------------------------------------------------|
| **Fabián Pérez**      | [LinkedIn](https://ar.linkedin.com/in/ffperezs) |
| **Nicolás Huanca**    | [LinkedIn](https://www.linkedin.com/in/nicolas-huanca) |
| **Matias Mazzitelli** | [LinkedIn](https://ar.linkedin.com/in/mnm-dev) |
| **Pablo Mejillone**  | [LinkedIn](https://bo.linkedin.com/in/pablo-mejillone-98b07425a) |

## Dependencias Clave

- **Spring Boot 3.3.3:** Marco para el desarrollo de aplicaciones.
- **Mariadb:** Base de datos relacional.
- **JWT:** Autenticación mediante JSON Web Tokens.
- **Spring Security:** Seguridad para la aplicación.
- **Oauth2-client:** Autenticación de usuarios a través de Google.
- **Spring Boot Starter Email:** Envío de correos electrónicos de manera automatizada.
- **Cloudinary:** Almacenamiento y manejo de imágenes en la nube.
- **Thymeleaf:** Motor de plantillas para la renderización del lado del servidor.
- **Swagger:** Documentación de API.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- **Java 17**
- **Maven** 3.6.0+
- **Mariadb** (con una base de datos configurada)

## Configuración del proyecto

### Variables de entorno

El proyecto depende de variables de entorno para la correcta configuración de la base de datos, correo y autenticación JWT. Puedes establecer estas variables en un archivo `.env` o configurarlas directamente en tu entorno.

Base de Datos:
- SERVER_PORT: Puerto en el que se ejecuta la aplicación.
- DB_URL: URL de la base de datos.
- DB_USER: Usuario de la base de datos.
- DB_PASSWORD: Contraseña de la base de datos.

Google Login:
- GOOGLE_CLIENT_ID: ID de cliente para autenticación con Google.
- GOOGLE_CLIENT_SECRET: Secreto de cliente para autenticación con Google.
- GOOGLE_SCOPE: Alcance de la autenticación con Google (profile,email).

JWT (Autenticación):
- JWT_SECRET: Clave secreta para firmar los tokens JWT.
- JWT_EXPIRATION: Tiempo de expiración del token JWT (en milisegundos).

Cloudinary:
- CLOUDINARY_API_KEY: Clave de API de Cloudinary.
- CLOUDINARY_API_SECRET: Secreto de API de Cloudinary.
- CLOUDINARY_CLOUD_NAME: Nombre del cloud de Cloudinary.

Correo Electrónico:
- EMAIL_USER: Usuario de la cuenta de correo (ej: tu correo de Gmail).
- EMAIL_PASSWORD: Contraseña de la cuenta de correo.

Geolocalización:
- NOMINATIM_API_URL: URL de la API para geolocalización (por ejemplo, https://nominatim.openstreetmap.org/search).

### Base de datos

Este proyecto utiliza **Mariadb**. Asegúrate de que la base de datos esté configurada y corriendo. Puedes modificar las propiedades en el archivo `application.properties` si es necesario.

### Ejecutar el proyecto


1. Clona el repositorio:

    ```bash
    git clone <URL_DEL_REPOSITORIO>
    ```

2. Navega al directorio del proyecto:

    ```bash
    cd ecosistemas
    ```

3. Configura las variables de entorno necesarias o ajusta el archivo `application.properties` con los valores adecuados.

4. Ejecuta el proyecto utilizando Maven:

    ```bash
    mvn spring-boot:run
    ```

## Base de Datos

La aplicación está configurada para usar Mariadb como base de datos. Asegúrate de que el servidor de Mariadb esté corriendo y que los valores de conexión en `application.properties` estén configurados correctamente.

## Documentación de la API

La documentación de la API está disponible a través de Swagger. Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación en la siguiente URL:

```bash
http://localhost:8080/swagger-ui/index.html
```

## Contribuciones

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.

2. Crea una nueva rama:

    ```bash
    git checkout -b feature/nueva-funcionalidad
    ```

3. Realiza tus cambios.

    ```bash
    git commit -m 'Agrega nueva funcionalidad'
    ```

4. Haz push a la rama:

    ```bash
    git push origin feature/nueva-funcionalidad
    ```

5. Abre un Pull Request.

## Licencia

MIT


