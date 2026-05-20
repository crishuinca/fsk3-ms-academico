package cl.bohiggins.ms_academico.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.repository.CursoRepository;

@Component
public class CursoInicialSeeder {

	private static final Logger log = LoggerFactory.getLogger(CursoInicialSeeder.class);

	@Value("${app.curso-inicial.nivel}")
	private String nivel;

	@Value("${app.curso-inicial.letra}")
	private String letra;

	@Value("${app.curso-inicial.anio}")
	private Integer anio;

	@Value("${app.curso-inicial.profesor-jefe-rut}")
	private String profesorJefeRut;

	@Autowired
	private CursoRepository cursoRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void asegurarCursoInicial() {
		if (cursoRepository.count() > 0) {
			log.info("Ya existen cursos en la base de datos. No se crea el curso predeterminado.");
			return;
		}

		Curso curso = new Curso();
		curso.setNivel(nivel.trim());
		curso.setLetra(letra.trim());
		curso.setAnio(anio);
		curso.setProfesorJefeRut(profesorJefeRut.trim());
		Curso guardado = cursoRepository.save(curso);

		log.info(
				"Curso predeterminado creado al iniciar: id={}, nivel={} letra={} anio={}",
				guardado.getId(),
				guardado.getNivel(),
				guardado.getLetra(),
				guardado.getAnio());
	}
}
