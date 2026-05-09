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

import java.time.LocalDate;

@Entity
@Table(name = "estudiantes", uniqueConstraints = @UniqueConstraint(name = "uk_estudiante_rut", columnNames = "rut"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "curso")
public class Estudiante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 12)
	private String rut;

	@Column(nullable = false, length = 120)
	private String nombres;

	@Column(name = "apellido_paterno", nullable = false, length = 80)
	private String apellidoPaterno;

	@Column(name = "apellido_materno", nullable = false, length = 80)
	private String apellidoMaterno;

	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;

	@Column(length = 160)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "curso_id", nullable = false)
	private Curso curso;
}
