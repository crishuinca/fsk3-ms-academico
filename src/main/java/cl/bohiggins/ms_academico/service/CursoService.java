package cl.bohiggins.ms_academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository repositorio;

	public Curso guardarCurso(Curso c) {
		return repositorio.save(c);
	}

	public List<Curso> guardarCursos(List<Curso> ls_c) {
		return repositorio.saveAll(ls_c);
	}

	public List<Curso> obtenerCursos() {
		return repositorio.findAll();
	}

	public Curso obtenerCursoID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public Curso modificarCurso(Curso c_mod) {
		Curso c = repositorio.findById(c_mod.getId()).orElse(null);
		if (c == null) {
			return null;
		}
		c.setNivel(c_mod.getNivel());
		c.setLetra(c_mod.getLetra());
		c.setAnio(c_mod.getAnio());
		c.setProfesorJefeRut(c_mod.getProfesorJefeRut());
		return repositorio.save(c);
	}

	public String borrarCurso(Long id) {
		repositorio.deleteById(id);
		return "Curso eliminado correctamente.";
	}
}
