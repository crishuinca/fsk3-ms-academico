package cl.bohiggins.ms_academico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI msAcademicoOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("MS Academico - Libro de clases digital")
						.description("API del microservicio de gestion academica (cursos, estudiantes, asignaturas, evaluaciones y notas).")
						.version("1.0.0"));
	}
}
