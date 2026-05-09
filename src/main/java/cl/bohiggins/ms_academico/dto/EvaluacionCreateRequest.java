package cl.bohiggins.ms_academico.dto;

import cl.bohiggins.ms_academico.entity.TipoEvaluacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Datos para crear una evaluación (Chile: semestre 1 o 2, ponderación en %)")
public record EvaluacionCreateRequest(
		@NotBlank @Schema(example = "Prueba coef. 2") String nombre,
		@NotNull TipoEvaluacion tipo,
		@NotNull LocalDate fecha,
		@NotNull @Min(1) @Max(2) @Schema(description = "1 o 2", example = "1") Integer semestre,
		@NotNull @Schema(example = "2026") Integer anio,
		@NotNull @Schema(description = "Porcentaje, ej. 30.00", example = "30.00") BigDecimal ponderacion,
		@NotNull @Schema(example = "1") Long asignaturaId,
		@NotNull @Schema(example = "1") Long cursoId
) {
}
