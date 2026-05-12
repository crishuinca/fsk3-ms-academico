package cl.bohiggins.ms_academico.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_academico.dto.EstudianteCreateRequest;
import cl.bohiggins.ms_academico.dto.EvaluacionCreateRequest;
import cl.bohiggins.ms_academico.dto.NotaCreateRequest;
import cl.bohiggins.ms_academico.entity.Asignatura;
import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.entity.Evaluacion;
import cl.bohiggins.ms_academico.entity.Nota;
import cl.bohiggins.ms_academico.entity.TipoEvaluacion;
import cl.bohiggins.ms_academico.service.AsignaturaService;
import cl.bohiggins.ms_academico.service.CursoService;
import cl.bohiggins.ms_academico.service.EstudianteService;
import cl.bohiggins.ms_academico.service.EvaluacionService;
import cl.bohiggins.ms_academico.service.NotaService;

@ExtendWith(MockitoExtension.class)
class AcademicoControllerTest {

	@Mock
	private CursoService cursoService;

	@Mock
	private AsignaturaService asignaturaService;

	@Mock
	private EstudianteService estudianteService;

	@Mock
	private EvaluacionService evaluacionService;

	@Mock
	private NotaService notaService;

	@InjectMocks
	private CursoController cursoController;

	@InjectMocks
	private AsignaturaController asignaturaController;

	@InjectMocks
	private EstudianteController estudianteController;

	@InjectMocks
	private EvaluacionController evaluacionController;

	@InjectMocks
	private NotaController notaController;

	@Test
	void cursoController_delegaOperaciones() {
		Curso curso = new Curso();
		curso.setId(1L);
		List<Curso> cursos = List.of(curso);
		when(cursoService.guardarCurso(curso)).thenReturn(curso);
		when(cursoService.guardarCursos(cursos)).thenReturn(cursos);
		when(cursoService.obtenerCursos()).thenReturn(cursos);
		when(cursoService.obtenerCursoID(1L)).thenReturn(curso);
		when(cursoService.modificarCurso(curso)).thenReturn(curso);
		when(cursoService.borrarCurso(1L)).thenReturn("Curso eliminado correctamente.");

		Curso creado = cursoController.c_guardarCurso(curso);
		assertSame(curso, creado);
		assertNull(curso.getId());
		assertSame(cursos, cursoController.c_guardarCursos(cursos));
		assertSame(cursos, cursoController.c_obtenerCursos());
		assertSame(curso, cursoController.c_obtenerCursoID(1L));
		assertSame(curso, cursoController.c_modificarCurso(curso));
		assertEquals("Curso eliminado correctamente.", cursoController.c_borrarCurso(1L));
	}

	@Test
	void asignaturaController_delegaOperaciones() {
		Asignatura asignatura = new Asignatura();
		asignatura.setId(1L);
		List<Asignatura> asignaturas = List.of(asignatura);
		when(asignaturaService.guardarAsignatura(asignatura)).thenReturn(asignatura);
		when(asignaturaService.guardarAsignaturas(asignaturas)).thenReturn(asignaturas);
		when(asignaturaService.obtenerAsignaturas()).thenReturn(asignaturas);
		when(asignaturaService.obtenerAsignaturaID(1L)).thenReturn(asignatura);
		when(asignaturaService.modificarAsignatura(asignatura)).thenReturn(asignatura);
		when(asignaturaService.borrarAsignatura(1L)).thenReturn("Asignatura eliminada correctamente.");

		Asignatura creada = asignaturaController.c_guardarAsignatura(asignatura);
		assertSame(asignatura, creada);
		assertNull(asignatura.getId());
		assertSame(asignaturas, asignaturaController.c_guardarAsignaturas(asignaturas));
		assertSame(asignaturas, asignaturaController.c_obtenerAsignaturas());
		assertSame(asignatura, asignaturaController.c_obtenerAsignaturaID(1L));
		assertSame(asignatura, asignaturaController.c_modificarAsignatura(asignatura));
		assertEquals("Asignatura eliminada correctamente.", asignaturaController.c_borrarAsignatura(1L));
	}

	@Test
	void estudianteController_delegaOperaciones() {
		Estudiante estudiante = new Estudiante();
		estudiante.setId(1L);
		EstudianteCreateRequest request = new EstudianteCreateRequest(
				1L,
				"21827564-8",
				"Cristobal",
				"Huinca",
				"Aravena",
				LocalDate.of(2010, 4, 28),
				"cristobal.huinca@colegio.cl"
		);
		List<Estudiante> estudiantes = List.of(estudiante);
		when(estudianteService.guardarEstudiante(request)).thenReturn(estudiante);
		when(estudianteService.obtenerEstudiantes()).thenReturn(estudiantes);
		when(estudianteService.obtenerEstudiantesPorCurso(1L)).thenReturn(estudiantes);
		when(estudianteService.obtenerEstudianteID(1L)).thenReturn(estudiante);
		when(estudianteService.obtenerEstudianteRut("21827564-8")).thenReturn(estudiante);
		when(estudianteService.modificarEstudiante(estudiante)).thenReturn(estudiante);
		when(estudianteService.borrarEstudiante(1L)).thenReturn("Estudiante eliminado correctamente.");

		assertSame(estudiante, estudianteController.c_guardarEstudiante(request));
		assertSame(estudiantes, estudianteController.c_obtenerEstudiantes());
		assertSame(estudiantes, estudianteController.c_obtenerEstudiantesPorCurso(1L));
		assertSame(estudiante, estudianteController.c_obtenerEstudianteID(1L));
		assertSame(estudiante, estudianteController.c_obtenerEstudianteRut("21827564-8"));
		assertSame(estudiante, estudianteController.c_modificarEstudiante(estudiante));
		assertEquals("Estudiante eliminado correctamente.", estudianteController.c_borrarEstudiante(1L));
	}

	@Test
	void evaluacionController_delegaOperaciones() {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setId(1L);
		EvaluacionCreateRequest request = new EvaluacionCreateRequest(
				"Prueba de fracciones",
				TipoEvaluacion.PRUEBA,
				LocalDate.of(2026, 5, 10),
				1,
				2026,
				1L,
				1L
		);
		List<Evaluacion> evaluaciones = List.of(evaluacion);
		when(evaluacionService.guardarEvaluacion(request)).thenReturn(evaluacion);
		when(evaluacionService.obtenerEvaluaciones()).thenReturn(evaluaciones);
		when(evaluacionService.obtenerEvaluacionesPorCurso(1L)).thenReturn(evaluaciones);
		when(evaluacionService.obtenerEvaluacionID(1L)).thenReturn(evaluacion);
		when(evaluacionService.modificarEvaluacion(evaluacion)).thenReturn(evaluacion);
		when(evaluacionService.borrarEvaluacion(1L)).thenReturn("Evaluacion eliminada correctamente.");

		assertSame(evaluacion, evaluacionController.c_guardarEvaluacion(request));
		assertSame(evaluaciones, evaluacionController.c_obtenerEvaluaciones());
		assertSame(evaluaciones, evaluacionController.c_obtenerEvaluacionesPorCurso(1L));
		assertSame(evaluacion, evaluacionController.c_obtenerEvaluacionID(1L));
		assertSame(evaluacion, evaluacionController.c_modificarEvaluacion(evaluacion));
		assertEquals("Evaluacion eliminada correctamente.", evaluacionController.c_borrarEvaluacion(1L));
	}

	@Test
	void notaController_delegaOperaciones() {
		Nota nota = new Nota();
		nota.setId(1L);
		NotaCreateRequest request = new NotaCreateRequest(1L, 1L, new BigDecimal("6.0"), "Buen trabajo");
		List<Nota> notas = List.of(nota);
		when(notaService.guardarNota(request)).thenReturn(nota);
		when(notaService.obtenerNotas()).thenReturn(notas);
		when(notaService.obtenerNotasPorEstudiante(1L)).thenReturn(notas);
		when(notaService.obtenerNotaID(1L)).thenReturn(nota);
		when(notaService.modificarNota(nota)).thenReturn(nota);
		when(notaService.borrarNota(1L)).thenReturn("Nota eliminada correctamente.");

		assertSame(nota, notaController.c_guardarNota(request));
		assertSame(notas, notaController.c_obtenerNotas());
		assertSame(notas, notaController.c_obtenerNotasPorEstudiante(1L));
		assertSame(nota, notaController.c_obtenerNotaID(1L));
		assertSame(nota, notaController.c_modificarNota(nota));
		assertEquals("Nota eliminada correctamente.", notaController.c_borrarNota(1L));
	}
}
