package cl.bohiggins.ms_academico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
		name = "notas",
		uniqueConstraints = @UniqueConstraint(name = "uk_nota_estudiante_evaluacion", columnNames = { "estudiante_id", "evaluacion_id" })
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "estudiante", "evaluacion" })
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, precision = 3, scale = 1)
	private BigDecimal valor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "estudiante_id", nullable = false)
	private Estudiante estudiante;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "evaluacion_id", nullable = false)
	private Evaluacion evaluacion;

	@Column(length = 500)
	private String observacion;

	@Column(name = "fecha_registro", nullable = false)
	private LocalDateTime fechaRegistro;
}
