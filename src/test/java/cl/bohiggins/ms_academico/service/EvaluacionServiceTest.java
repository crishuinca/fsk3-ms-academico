package cl.bohiggins.ms_academico.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import cl.bohiggins.ms_academico.dto.EvaluacionCreateRequest;
import cl.bohiggins.ms_academico.entity.Asignatura;
import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.entity.Evaluacion;
import cl.bohiggins.ms_academico.entity.TipoEvaluacion;
import cl.bohiggins.ms_academico.repository.AsignaturaRepository;
import cl.bohiggins.ms_academico.repository.CursoRepository;
import cl.bohiggins.ms_academico.repository.EvaluacionRepository;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {

	@Mock
	private EvaluacionRepository repositorio;

	@Mock
	private AsignaturaRepository asignaturaRepositorio;

	@Mock
	private CursoRepository cursoRepositorio;

	@InjectMocks
	private EvaluacionService servicio;

	@Test
	void guardarEvaluacion_asociaAsignaturaYCurso() {
		Asignatura asignatura = new Asignatura();
		asignatura.setId(1L);
		Curso curso = new Curso();
		curso.setId(2L);
		EvaluacionCreateRequest request = new EvaluacionCreateRequest(
				"Prueba de fracciones",
				TipoEvaluacion.PRUEBA,
				LocalDate.of(2026, 5, 10),
				1,
				2026,
				1L,
				2L
		);
		when(asignaturaRepositorio.findById(1L)).thenReturn(Optional.of(asignatura));
		when(cursoRepositorio.findById(2L)).thenReturn(Optional.of(curso));
		when(repositorio.save(any(Evaluacion.class))).thenAnswer(invocation -> {
			Evaluacion evaluacion = invocation.getArgument(0);
			evaluacion.setId(30L);
			return evaluacion;
		});

		Evaluacion resultado = servicio.guardarEvaluacion(request);

		assertEquals(30L, resultado.getId());
		assertEquals("Prueba de fracciones", resultado.getNombre());
		assertEquals(TipoEvaluacion.PRUEBA, resultado.getTipo());
		assertSame(asignatura, resultado.getAsignatura());
		assertSame(curso, resultado.getCurso());
		verify(repositorio).save(any(Evaluacion.class));
	}

	@Test
	void guardarEvaluacion_asignaturaNoExiste_lanzaError() {
		EvaluacionCreateRequest request = new EvaluacionCreateRequest(
				"Prueba de fracciones",
				TipoEvaluacion.PRUEBA,
				LocalDate.of(2026, 5, 10),
				1,
				2026,
				99L,
				2L
		);
		when(asignaturaRepositorio.findById(99L)).thenReturn(Optional.empty());

		IllegalArgumentException error = assertThrows(
				IllegalArgumentException.class,
				() -> servicio.guardarEvaluacion(request)
		);

		assertEquals("No existe la asignatura.", error.getMessage());
		verify(cursoRepositorio, never()).findById(any());
		verify(repositorio, never()).save(any(Evaluacion.class));
	}

	@Test
	void obtenerEvaluacionesPorCurso_retornaListaDelRepositorio() {
		List<Evaluacion> evaluaciones = List.of(new Evaluacion(), new Evaluacion());
		when(repositorio.findByCursoId(2L)).thenReturn(evaluaciones);

		List<Evaluacion> resultado = servicio.obtenerEvaluacionesPorCurso(2L);

		assertSame(evaluaciones, resultado);
		assertEquals(2, resultado.size());
	}

	@Test
	void obtenerEvaluacionID_retornaEvaluacionEncontrada() {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setId(10L);
		when(repositorio.findById(10L)).thenReturn(Optional.of(evaluacion));

		Evaluacion resultado = servicio.obtenerEvaluacionID(10L);

		assertSame(evaluacion, resultado);
	}

	@Test
	void modificarEvaluacion_noExiste_retornaNull() {
		Evaluacion modificada = new Evaluacion();
		modificada.setId(99L);
		when(repositorio.findById(99L)).thenReturn(Optional.empty());

		Evaluacion resultado = servicio.modificarEvaluacion(modificada);

		assertNull(resultado);
	}

	@Test
	void guardarEvaluacion_cursoNoExiste_lanzaError() {
		Asignatura asignatura = new Asignatura();
		asignatura.setId(1L);
		EvaluacionCreateRequest request = new EvaluacionCreateRequest(
				"Prueba",
				TipoEvaluacion.PRUEBA,
				LocalDate.of(2026, 5, 10),
				1,
				2026,
				1L,
				99L
		);
		when(asignaturaRepositorio.findById(1L)).thenReturn(Optional.of(asignatura));
		when(cursoRepositorio.findById(99L)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> servicio.guardarEvaluacion(request));
	}

	@Test
	void modificarEvaluacion_actualizaDatosSiExiste() {
		Evaluacion actual = new Evaluacion();
		actual.setId(10L);
		Evaluacion modificada = new Evaluacion();
		modificada.setId(10L);
		modificada.setNombre("Control 2");
		when(repositorio.findById(10L)).thenReturn(Optional.of(actual));
		when(repositorio.save(any(Evaluacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Evaluacion resultado = servicio.modificarEvaluacion(modificada);

		assertEquals("Control 2", resultado.getNombre());
	}

	@Test
	void obtenerEvaluaciones_retornaListaDelRepositorio() {
		List<Evaluacion> evaluaciones = List.of(new Evaluacion());
		when(repositorio.findAll()).thenReturn(evaluaciones);

		assertSame(evaluaciones, servicio.obtenerEvaluaciones());
	}

	@Test
	void borrarEvaluacion_eliminaPorId() {
		String resultado = servicio.borrarEvaluacion(1L);

		assertEquals("Evaluacion eliminada correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}
}
