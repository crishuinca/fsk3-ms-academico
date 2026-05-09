package cl.bohiggins.ms_academico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
		name = "cursos",
		uniqueConstraints = @UniqueConstraint(name = "uk_curso_nivel_letra_anio", columnNames = { "nivel", "letra", "anio" })
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String nivel;

	@NotBlank
	@Column(nullable = false, length = 8)
	private String letra;

	@NotNull
	@Column(nullable = false)
	private Integer anio;

	@NotBlank
	@Column(name = "profesor_jefe_rut", nullable = false, length = 12)
	private String profesorJefeRut;
}
