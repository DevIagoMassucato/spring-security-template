# spring-security-template

The `spring-security-template` project is a monolithic REST API focused on authentication architecture

The API provides complete access control, from basic to advanced, from Java 11 with Spring Boot 2.x to Java 17 with Spring Boot 3.5.x

## Technologies

- Java (11 and 17)
- Spring Boot (2.x and 3.5.x)
- Spring Security
- JWT
- OAuth2 Client and Resource Server
- Docker
- MySQL

## API Details

- User registration and management are handled by the institution's IT department

- Every registered user must have an email address and at least one Role

- A User can be assigned multiple Roles

- Users can authenticate using either their username and password or their Google account

- Google authentication is available only when the user's registered email address is associated with a Google account

- Each successful login creates a new Session and records the device used for authentication

- Users can update their account information and terminate all of their active sessions

- The project initially used a custom JWT authentication filter to secure protected endpoints. The authentication mechanism was later migrated to Spring Security's OAuth2 Resource Server

- The Password Recovery feature only works when the [`notification-service`]([LINK](https://github.com/DevIagoMassucato/notification-service)) API is running

- Token, Session, and Password Recovery code expiration times can be configured in `application.yml`

### JWT Secret Configuration

- A JWT secret key must be configured before running the application

- On Windows, generate a secure Base64-encoded key by running the following command in CMD:

```cmd
powershell -Command "[Convert]::ToBase64String((1..32 | % {Get-Random -Maximum 256}))"
```

- Copy the generated key and set it as the JWT_SECRET_KEY environment variable:
```cmd
setx JWT_SECRET_KEY "YOUR_SECRET_KEY_HERE"
```

- After setting the environment variable, restart the terminal or application for the change to take effect
