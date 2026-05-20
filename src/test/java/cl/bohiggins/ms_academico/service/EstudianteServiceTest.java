package cl.bohiggins.ms_academico.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_academico.dto.EstudianteCreateRequest;
import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.repository.CursoRepository;
import cl.bohiggins.ms_academico.repository.EstudianteRepository;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

	@Mock
	private EstudianteRepository repositorio;

	@Mock
	private CursoRepository cursoRepositorio;

	@InjectMocks
	private EstudianteService servicio;

	@Test
	void guardarEstudiante_asociaCursoExistente() {
		Curso curso = new Curso();
		curso.setId(1L);
		curso.setNivel("2 Medio");
		curso.setLetra("A");
		curso.setAnio(2026);
		curso.setProfesorJefeRut("12345678-9");

		EstudianteCreateRequest request = new EstudianteCreateRequest(
				1L,
				"21827564-8",
				"Cristobal",
				"Huinca",
				"Aravena",
				LocalDate.of(2010, 4, 28),
				"cristobal.huinca@colegio.cl"
		);

		when(repositorio.findByRut("21827564-8")).thenReturn(Optional.empty());
		when(cursoRepositorio.findById(1L)).thenReturn(Optional.of(curso));
		when(repositorio.save(any(Estudiante.class))).thenAnswer(invocation -> {
			Estudiante estudiante = invocation.getArgument(0);
			estudiante.setId(10L);
			return estudiante;
		});

		Estudiante resultado = servicio.guardarEstudiante(request);

		assertNotNull(resultado);
		assertEquals(10L, resultado.getId());
		assertEquals("21827564-8", resultado.getRut());
		assertEquals("Cristobal", resultado.getNombres());
		assertSame(curso, resultado.getCurso());
		verify(cursoRepositorio).findById(1L);
		verify(repositorio).save(any(Estudiante.class));
	}

	@Test
	void obtenerProximoId_sinEstudiantes_retornaUno() {
		when(repositorio.calcularProximoId()).thenReturn(1L);

		assertEquals(1L, servicio.obtenerProximoId());
	}

	@Test
	void guardarEstudiante_cursoNoExiste_lanzaError() {
		EstudianteCreateRequest request = new EstudianteCreateRequest(
				99L,
				"21827564-8",
				"Cristobal",
				"Huinca",
				"Aravena",
				LocalDate.of(2010, 4, 28),
				"cristobal.huinca@colegio.cl"
		);

		when(cursoRepositorio.findById(99L)).thenReturn(Optional.empty());

		IllegalArgumentException error = assertThrows(
				IllegalArgumentException.class,
				() -> servicio.guardarEstudiante(request)
		);

		assertEquals("No existe el curso.", error.getMessage());
		verify(repositorio, never()).save(any(Estudiante.class));
	}

	@Test
	void obtenerEstudianteRut_retornaEstudianteEncontrado() {
		Estudiante estudiante = new Estudiante();
		estudiante.setId(5L);
		estudiante.setRut("21827564-8");

		when(repositorio.findByRut("21827564-8")).thenReturn(Optional.of(estudiante));

		Estudiante resultado = servicio.obtenerEstudianteRut("21827564-8");

		assertSame(estudiante, resultado);
		verify(repositorio).findByRut("21827564-8");
	}

	@Test
	void obtenerEstudiantesPorCurso_retornaListaDelRepositorio() {
		List<Estudiante> estudiantes = List.of(new Estudiante(), new Estudiante());
		when(repositorio.findByCursoId(1L)).thenReturn(estudiantes);

		List<Estudiante> resultado = servicio.obtenerEstudiantesPorCurso(1L);

		assertSame(estudiantes, resultado);
		assertEquals(2, resultado.size());
	}

	@Test
	void obtenerEstudianteID_retornaEstudianteEncontrado() {
		Estudiante estudiante = new Estudiante();
		estudiante.setId(5L);
		when(repositorio.findById(5L)).thenReturn(Optional.of(estudiante));

		Estudiante resultado = servicio.obtenerEstudianteID(5L);

		assertSame(estudiante, resultado);
	}

	@Test
	void borrarEstudiante_eliminaPorId() {
		String resultado = servicio.borrarEstudiante(1L);

		assertEquals("Estudiante eliminado correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}
}
