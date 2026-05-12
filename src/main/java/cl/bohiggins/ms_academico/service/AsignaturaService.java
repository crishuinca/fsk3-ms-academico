package cl.bohiggins.ms_academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_academico.entity.Asignatura;
import cl.bohiggins.ms_academico.repository.AsignaturaRepository;

@Service
public class AsignaturaService {

	@Autowired
	private AsignaturaRepository repositorio;

	public Asignatura guardarAsignatura(Asignatura a) {
		return repositorio.save(a);
	}

	public List<Asignatura> guardarAsignaturas(List<Asignatura> ls_a) {
		return repositorio.saveAll(ls_a);
	}

	public List<Asignatura> obtenerAsignaturas() {
		return repositorio.findAll();
	}

	public Asignatura obtenerAsignaturaID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public Asignatura modificarAsignatura(Asignatura a_mod) {
		Asignatura a = repositorio.findById(a_mod.getId()).orElse(null);
		if (a == null) {
			return null;
		}
		a.setNombre(a_mod.getNombre());
		a.setCodigo(a_mod.getCodigo());
		a.setProfesorRut(a_mod.getProfesorRut());
		return repositorio.save(a);
	}

	public String borrarAsignatura(Long id) {
		repositorio.deleteById(id);
		return "Asignatura eliminada correctamente.";
	}
}
