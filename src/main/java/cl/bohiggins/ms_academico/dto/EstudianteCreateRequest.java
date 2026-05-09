package cl.bohiggins.ms_academico.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para registrar un estudiante en un curso")
public record EstudianteCreateRequest(
		@NotNull @Schema(description = "ID del curso", example = "1") Long cursoId,
		@NotBlank @Schema(example = "21345678-9") String rut,
		@NotBlank @Schema(example = "Armando") String nombres,
		@NotBlank @Schema(example = "Casas") String apellidoPaterno,
		@NotBlank @Schema(example = "González") String apellidoMaterno,
		LocalDate fechaNacimiento,
		@Schema(example = "juan.perez@alumno.colegio.cl") String email
) {
}
