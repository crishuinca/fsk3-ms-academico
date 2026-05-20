package cl.bohiggins.ms_academico.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.repository.CursoRepository;

@ExtendWith(MockitoExtension.class)
class CursoInicialSeederTest {

	@Mock
	private CursoRepository cursoRepository;

	@InjectMocks
	private CursoInicialSeeder seeder;

	@BeforeEach
	void configurarPropiedades() {
		ReflectionTestUtils.setField(seeder, "nivel", "2 Medio");
		ReflectionTestUtils.setField(seeder, "letra", "A");
		ReflectionTestUtils.setField(seeder, "anio", 2026);
		ReflectionTestUtils.setField(seeder, "profesorJefeRut", "12345678-9");
	}

	@Test
	void asegurarCursoInicial_creaCursoCuandoNoHayCursos() {
		when(cursoRepository.count()).thenReturn(0L);
		when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> {
			Curso curso = invocation.getArgument(0);
			curso.setId(1L);
			return curso;
		});

		seeder.asegurarCursoInicial();

		ArgumentCaptor<Curso> captor = ArgumentCaptor.forClass(Curso.class);
		verify(cursoRepository).save(captor.capture());
		Curso curso = captor.getValue();
		org.junit.jupiter.api.Assertions.assertEquals("2 Medio", curso.getNivel());
		org.junit.jupiter.api.Assertions.assertEquals("A", curso.getLetra());
		org.junit.jupiter.api.Assertions.assertEquals(2026, curso.getAnio());
	}

	@Test
	void asegurarCursoInicial_noCreaSiYaExistenCursos() {
		when(cursoRepository.count()).thenReturn(1L);

		seeder.asegurarCursoInicial();

		verify(cursoRepository, never()).save(any());
	}
}
