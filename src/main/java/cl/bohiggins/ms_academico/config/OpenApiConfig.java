package cl.bohiggins.ms_academico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI msAcademicoOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("MS Academico - Libro de clases digital")
						.description("API del microservicio de gestion academica (cursos, estudiantes, asignaturas, evaluaciones y notas). Escala de notas Chile 1.0 a 7.0.")
						.version("1.0.0"));
	}
}
