# Smart Work Management

Lightweight Spring Boot application for managing tasks and users with OAuth2 login (Google) and an in-memory H2 database for development.

**Features**
- **Task management**: create, edit, delete tasks (server-side JPA entities).
- **User management**: roles and users with basic UI templates.
- **Authentication**: OAuth2 client (Google) via Spring Security.
- **In-memory DB**: H2 for quick development and testing with console enabled.

**Tech Stack**
- **Java**: 17
- **Spring Boot**: 4.x
- **Spring Security**, **Spring Data JPA**, **Thymeleaf**, **H2**

**Prerequisites**
- JDK 17 installed and `JAVA_HOME` configured.
- Maven (or use the included wrapper `mvnw` / `mvnw.cmd`).

**Run (Windows)**
```cmd
mvnw.cmd spring-boot:run
```

**Run (Unix / macOS)**
```bash
./mvnw spring-boot:run
```

Or build and run the jar:
```bash
mvn package
java -jar target/task-management-0.0.1-SNAPSHOT.jar
```

**Configuration**
- Application properties are in [src/main/resources/application.properties](src/main/resources/application.properties).
- Google OAuth client is configured via:

  - `spring.security.oauth2.client.registration.google.client-id`
  - `spring.security.oauth2.client.registration.google.client-secret`

  Prefer setting client credentials as environment variables or externalized config to avoid committing secrets. Example (Linux/macOS):

```bash
export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your-client-id
export SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your-client-secret
```

**Project Layout**
- `src/main/java` — application source code (controllers, entities, repositories, services).
- `src/main/resources/templates` — Thymeleaf templates (UI views).
- `src/main/resources/application.properties` — runtime configuration.

**Tests**
- Run unit/integration tests with:
```bash
mvn test
```

**Notes**
- This project uses an example Google OAuth client id/secret in `application.properties`; replace these with your own credentials for production.
- For production, switch from H2 to a persistent database and secure secrets with a vault or environment variables.

**License & Contributing**
- Small demo project — feel free to open issues or PRs.
