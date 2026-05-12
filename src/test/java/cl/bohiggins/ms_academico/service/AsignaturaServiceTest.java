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

import cl.bohiggins.ms_academico.entity.Asignatura;
import cl.bohiggins.ms_academico.repository.AsignaturaRepository;

@ExtendWith(MockitoExtension.class)
class AsignaturaServiceTest {

	@Mock
	private AsignaturaRepository repositorio;

	@InjectMocks
	private AsignaturaService servicio;

	@Test
	void guardarAsignatura_delegaEnRepositorio() {
		Asignatura asignatura = crearAsignatura(1L, "Matematica", "MAT");
		when(repositorio.save(asignatura)).thenReturn(asignatura);

		Asignatura resultado = servicio.guardarAsignatura(asignatura);

		assertSame(asignatura, resultado);
		verify(repositorio).save(asignatura);
	}

	@Test
	void obtenerAsignaturas_retornaListaDelRepositorio() {
		List<Asignatura> asignaturas = List.of(
				crearAsignatura(1L, "Matematica", "MAT"),
				crearAsignatura(2L, "Lenguaje", "LEN")
		);
		when(repositorio.findAll()).thenReturn(asignaturas);

		List<Asignatura> resultado = servicio.obtenerAsignaturas();

		assertEquals(2, resultado.size());
		assertSame(asignaturas, resultado);
	}

	@Test
	void guardarAsignaturas_delegaEnRepositorio() {
		List<Asignatura> asignaturas = List.of(
				crearAsignatura(1L, "Matematica", "MAT"),
				crearAsignatura(2L, "Lenguaje", "LEN")
		);
		when(repositorio.saveAll(asignaturas)).thenReturn(asignaturas);

		List<Asignatura> resultado = servicio.guardarAsignaturas(asignaturas);

		assertSame(asignaturas, resultado);
		verify(repositorio).saveAll(asignaturas);
	}

	@Test
	void obtenerAsignaturaID_retornaAsignaturaEncontrada() {
		Asignatura asignatura = crearAsignatura(1L, "Matematica", "MAT");
		when(repositorio.findById(1L)).thenReturn(Optional.of(asignatura));

		Asignatura resultado = servicio.obtenerAsignaturaID(1L);

		assertSame(asignatura, resultado);
	}

	@Test
	void modificarAsignatura_actualizaDatosSiExiste() {
		Asignatura actual = crearAsignatura(1L, "Matematica", "MAT");
		Asignatura modificada = crearAsignatura(1L, "Fisica", "FIS");
		when(repositorio.findById(1L)).thenReturn(Optional.of(actual));
		when(repositorio.save(any(Asignatura.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Asignatura resultado = servicio.modificarAsignatura(modificada);

		assertEquals("Fisica", resultado.getNombre());
		assertEquals("FIS", resultado.getCodigo());
		assertEquals("12345678-9", resultado.getProfesorRut());
		verify(repositorio).save(actual);
	}

	@Test
	void modificarAsignatura_noExiste_retornaNull() {
		Asignatura modificada = crearAsignatura(99L, "Fisica", "FIS");
		when(repositorio.findById(99L)).thenReturn(Optional.empty());

		Asignatura resultado = servicio.modificarAsignatura(modificada);

		assertNull(resultado);
	}

	@Test
	void borrarAsignatura_eliminaPorId() {
		String resultado = servicio.borrarAsignatura(1L);

		assertEquals("Asignatura eliminada correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}

	private Asignatura crearAsignatura(Long id, String nombre, String codigo) {
		Asignatura asignatura = new Asignatura();
		asignatura.setId(id);
		asignatura.setNombre(nombre);
		asignatura.setCodigo(codigo);
		asignatura.setProfesorRut("12345678-9");
		return asignatura;
	}
}
