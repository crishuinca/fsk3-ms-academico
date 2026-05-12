# ms-academico

Microservicio académico de la Plataforma de Libro de Clases Digital del Colegio Bernardo O'Higgins.

Este servicio administra la información académica principal: cursos, asignaturas, estudiantes, evaluaciones y notas. Es consumido directamente por el BFF `bff-libroclases`.

## Tecnologías

- Java 17
- Spring Boot 3.5.7
- Maven
- Spring Web
- Spring Data JPA
- H2 en modo archivo
- Bean Validation
- Swagger / OpenAPI con springdoc 2.8.6
- JUnit 5, Mockito y JaCoCo

## Puerto y URLs

El servicio corre en el puerto `8081`.

- API base: `http://localhost:8081/api/v1`
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`
- H2 console: `http://localhost:8081/h2-console`

Datos para H2:

- JDBC URL: `jdbc:h2:file:./data/academicodb`
- Usuario: `sa`
- Password: vacío

## Cómo ejecutar

Desde la carpeta del proyecto:

```powershell
cd "C:\Users\tobal\Desktop\Fullstack 3\ms-academico"
.\mvnw.cmd spring-boot:run
```

Para compilar:

```powershell
.\mvnw.cmd clean compile
```

## Tests y cobertura

Ejecutar tests con reporte de cobertura:

```powershell
.\mvnw.cmd verify
```

El reporte HTML de JaCoCo queda en:

```text
target/site/jacoco/index.html
```

Estado actual:

- 38 tests.
- Cobertura global aproximada: 76% por líneas.
- Regla JaCoCo: mínimo 60% para la capa `service`.

## Endpoints principales

Cursos:

- `POST /api/v1/addCurso`
- `POST /api/v1/addCursos`
- `GET /api/v1/cursos`
- `GET /api/v1/cursoByID/{id}`
- `PUT /api/v1/modificarCurso`
- `DELETE /api/v1/eliminarCurso/{id}`

Asignaturas:

- `POST /api/v1/addAsignatura`
- `POST /api/v1/addAsignaturas`
- `GET /api/v1/asignaturas`
- `GET /api/v1/asignaturaByID/{id}`
- `PUT /api/v1/modificarAsignatura`
- `DELETE /api/v1/eliminarAsignatura/{id}`

Estudiantes:

- `POST /api/v1/addEstudiante`
- `GET /api/v1/estudiantes`
- `GET /api/v1/estudiantesPorCurso/{cursoId}`
- `GET /api/v1/estudianteByID/{id}`
- `GET /api/v1/estudianteByRut/{rut}`
- `PUT /api/v1/modificarEstudiante`
- `DELETE /api/v1/eliminarEstudiante/{id}`

Evaluaciones:

- `POST /api/v1/addEvaluacion`
- `GET /api/v1/evaluaciones`
- `GET /api/v1/evaluacionesPorCurso/{cursoId}`
- `GET /api/v1/evaluacionByID/{id}`
- `PUT /api/v1/modificarEvaluacion`
- `DELETE /api/v1/eliminarEvaluacion/{id}`

Notas:

- `POST /api/v1/addNota`
- `GET /api/v1/notas`
- `GET /api/v1/notasPorEstudiante/{estudianteId}`
- `GET /api/v1/notaByID/{id}`
- `PUT /api/v1/modificarNota`
- `DELETE /api/v1/eliminarNota/{id}`

## Decisiones importantes

- Las notas usan escala chilena de `1.0` a `7.0`.
- La base de datos es persistente en `./data/academicodb`.
- La carpeta `data/` no se debe subir a GitHub.
- Los datos de prueba se cargan manualmente desde Swagger.
- Los controllers usan `/api/v1` y siguen el estilo del proyecto de referencia.

## Rol dentro de la arquitectura

`ms-academico` no es consumido directamente por el frontend. El flujo esperado es:

```text
Frontend React -> BFF -> ms-academico
```

El BFF consulta este microservicio para obtener estudiantes, cursos y datos académicos necesarios para armar las respuestas del libro de clases.
