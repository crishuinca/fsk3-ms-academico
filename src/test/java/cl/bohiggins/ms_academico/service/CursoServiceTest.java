package cl.bohiggins.ms_academico.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.repository.CursoRepository;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

	@Mock
	private CursoRepository repositorio;

	@InjectMocks
	private CursoService servicio;

	@Test
	void guardarCurso_delegaEnRepositorio() {
		Curso curso = crearCurso(1L, "2 Medio", "A", 2026);
		when(repositorio.save(curso)).thenReturn(curso);

		Curso resultado = servicio.guardarCurso(curso);

		assertSame(curso, resultado);
		verify(repositorio).save(curso);
	}

	@Test
	void obtenerCursos_retornaListaDelRepositorio() {
		List<Curso> cursos = List.of(
				crearCurso(1L, "1 Medio", "A", 2026),
				crearCurso(2L, "2 Medio", "B", 2026)
		);
		when(repositorio.findAll()).thenReturn(cursos);

		List<Curso> resultado = servicio.obtenerCursos();

		assertEquals(2, resultado.size());
		assertSame(cursos, resultado);
	}

	@Test
	void guardarCursos_delegaEnRepositorio() {
		List<Curso> cursos = List.of(
				crearCurso(1L, "1 Medio", "A", 2026),
				crearCurso(2L, "2 Medio", "B", 2026)
		);
		when(repositorio.saveAll(cursos)).thenReturn(cursos);

		List<Curso> resultado = servicio.guardarCursos(cursos);

		assertSame(cursos, resultado);
		verify(repositorio).saveAll(cursos);
	}

	@Test
	void obtenerCursoID_retornaCursoEncontrado() {
		Curso curso = crearCurso(1L, "2 Medio", "A", 2026);
		when(repositorio.findById(1L)).thenReturn(Optional.of(curso));

		Curso resultado = servicio.obtenerCursoID(1L);

		assertSame(curso, resultado);
	}

	@Test
	void modificarCurso_actualizaDatosSiExiste() {
		Curso actual = crearCurso(1L, "1 Medio", "A", 2026);
		Curso modificado = crearCurso(1L, "2 Medio", "B", 2026);
		when(repositorio.findById(1L)).thenReturn(Optional.of(actual));
		when(repositorio.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Curso resultado = servicio.modificarCurso(modificado);

		assertEquals("2 Medio", resultado.getNivel());
		assertEquals("B", resultado.getLetra());
		assertEquals(2026, resultado.getAnio());
		assertEquals("12345678-9", resultado.getProfesorJefeRut());
		verify(repositorio).save(actual);
	}

	@Test
	void modificarCurso_noExiste_retornaNull() {
		Curso modificado = crearCurso(99L, "2 Medio", "B", 2026);
		when(repositorio.findById(99L)).thenReturn(Optional.empty());

		Curso resultado = servicio.modificarCurso(modificado);

		assertNull(resultado);
	}

	@Test
	void borrarCurso_eliminaPorId() {
		String resultado = servicio.borrarCurso(1L);

		assertEquals("Curso eliminado correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}

	private Curso crearCurso(Long id, String nivel, String letra, Integer anio) {
		Curso curso = new Curso();
		curso.setId(id);
		curso.setNivel(nivel);
		curso.setLetra(letra);
		curso.setAnio(anio);
		curso.setProfesorJefeRut("12345678-9");
		return curso;
	}
}
