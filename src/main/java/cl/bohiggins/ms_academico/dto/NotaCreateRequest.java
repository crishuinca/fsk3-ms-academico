package cl.bohiggins.ms_academico.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Registro de nota 1.0 a 7.0 ")
public record NotaCreateRequest(
		@NotNull @Schema(example = "1") Long estudianteId,
		@NotNull @Schema(example = "1") Long evaluacionId,
		@NotNull @DecimalMin("1.0") @DecimalMax("7.0") @Schema(example = "5.5") BigDecimal valor,
		@Schema(example = "Recuperativa") String observacion
) {
}
