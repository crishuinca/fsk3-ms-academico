package cl.bohiggins.ms_academico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "evaluaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "asignatura", "curso" })
public class Evaluacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 200)
	private String nombre;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 32)
	private TipoEvaluacion tipo;

	@Column(nullable = false)
	private LocalDate fecha;

	@Column(nullable = false)
	private Integer semestre;

	@Column(nullable = false)
	private Integer anio;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "asignatura_id", nullable = false)
	private Asignatura asignatura;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "curso_id", nullable = false)
	private Curso curso;
}
