package cl.bohiggins.ms_academico.dto;

import java.time.LocalDate;

import cl.bohiggins.ms_academico.entity.TipoEvaluacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para crear una evaluación ")
public record EvaluacionCreateRequest(
		@NotBlank @Schema(example = "Prueba de Fracciones") String nombre,
		@NotNull TipoEvaluacion tipo,
		@NotNull LocalDate fecha,
		@NotNull @Min(1) @Max(2) @Schema(description = "1 o 2", example = "1") Integer semestre,
		@NotNull @Schema(example = "2026") Integer anio,
		@NotNull @Schema(example = "1") Long asignaturaId,
		@NotNull @Schema(example = "1") Long cursoId
) {
}
