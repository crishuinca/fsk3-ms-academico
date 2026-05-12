package cl.bohiggins.ms_academico.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.bohiggins.ms_academico.dto.NotaCreateRequest;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.entity.Evaluacion;
import cl.bohiggins.ms_academico.entity.Nota;
import cl.bohiggins.ms_academico.repository.EstudianteRepository;
import cl.bohiggins.ms_academico.repository.EvaluacionRepository;
import cl.bohiggins.ms_academico.repository.NotaRepository;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

	@Mock
	private NotaRepository repositorio;

	@Mock
	private EstudianteRepository estudianteRepositorio;

	@Mock
	private EvaluacionRepository evaluacionRepositorio;

	@InjectMocks
	private NotaService servicio;

	@Test
	void guardarNota_asociaEstudianteEvaluacionYFecha() {
		Estudiante estudiante = new Estudiante();
		estudiante.setId(1L);

		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setId(2L);

		NotaCreateRequest request = new NotaCreateRequest(
				1L,
				2L,
				new BigDecimal("6.2"),
				"Buen rendimiento"
		);

		when(estudianteRepositorio.findById(1L)).thenReturn(Optional.of(estudiante));
		when(evaluacionRepositorio.findById(2L)).thenReturn(Optional.of(evaluacion));
		when(repositorio.save(any(Nota.class))).thenAnswer(invocation -> {
			Nota nota = invocation.getArgument(0);
			nota.setId(20L);
			return nota;
		});

		Nota resultado = servicio.guardarNota(request);

		assertNotNull(resultado);
		assertEquals(20L, resultado.getId());
		assertEquals(new BigDecimal("6.2"), resultado.getValor());
		assertEquals("Buen rendimiento", resultado.getObservacion());
		assertSame(estudiante, resultado.getEstudiante());
		assertSame(evaluacion, resultado.getEvaluacion());
		assertNotNull(resultado.getFechaRegistro());
		verify(repositorio).save(any(Nota.class));
	}

	@Test
	void guardarNota_estudianteNoExiste_lanzaError() {
		NotaCreateRequest request = new NotaCreateRequest(
				99L,
				2L,
				new BigDecimal("5.5"),
				"Recuperativa"
		);

		when(estudianteRepositorio.findById(99L)).thenReturn(Optional.empty());

		IllegalArgumentException error = assertThrows(
				IllegalArgumentException.class,
				() -> servicio.guardarNota(request)
		);

		assertEquals("No existe el estudiante.", error.getMessage());
		verify(evaluacionRepositorio, never()).findById(any());
		verify(repositorio, never()).save(any(Nota.class));
	}

	@Test
	void modificarNota_fueraDeRangoChileno_lanzaError() {
		Nota actual = new Nota();
		actual.setId(1L);
		actual.setValor(new BigDecimal("5.0"));

		Nota modificada = new Nota();
		modificada.setId(1L);
		modificada.setValor(new BigDecimal("7.1"));
		modificada.setObservacion("Fuera de rango");

		when(repositorio.findById(1L)).thenReturn(Optional.of(actual));

		IllegalArgumentException error = assertThrows(
				IllegalArgumentException.class,
				() -> servicio.modificarNota(modificada)
		);

		assertEquals("La nota debe ser entre 1.0 y 7.0.", error.getMessage());
		verify(repositorio, never()).save(any(Nota.class));
	}

	@Test
	void obtenerNotasPorEstudiante_retornaListaDelRepositorio() {
		List<Nota> notas = List.of(new Nota(), new Nota());
		when(repositorio.findByEstudianteId(1L)).thenReturn(notas);

		List<Nota> resultado = servicio.obtenerNotasPorEstudiante(1L);

		assertSame(notas, resultado);
		assertEquals(2, resultado.size());
	}

	@Test
	void obtenerNotaID_retornaNotaEncontrada() {
		Nota nota = new Nota();
		nota.setId(5L);
		when(repositorio.findById(5L)).thenReturn(Optional.of(nota));

		Nota resultado = servicio.obtenerNotaID(5L);

		assertSame(nota, resultado);
	}

	@Test
	void borrarNota_eliminaPorId() {
		String resultado = servicio.borrarNota(1L);

		assertEquals("Nota eliminada correctamente.", resultado);
		verify(repositorio).deleteById(1L);
	}
}
