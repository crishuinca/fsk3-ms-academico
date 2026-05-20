package cl.bohiggins.ms_academico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_academico.dto.EstudianteCreateRequest;
import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.repository.CursoRepository;
import cl.bohiggins.ms_academico.repository.EstudianteRepository;

@Service
public class EstudianteService {

	@Autowired
	private EstudianteRepository repositorio;

	@Autowired
	private CursoRepository cursoRepositorio;

	public Long obtenerProximoId() {
		return repositorio.calcularProximoId();
	}

	public Estudiante guardarEstudiante(EstudianteCreateRequest req) {
		if (repositorio.findByRut(req.rut().trim()).isPresent()) {
			throw new IllegalArgumentException("Ya existe un estudiante con ese RUT.");
		}

		Curso curso = cursoRepositorio.findById(req.cursoId())
				.orElseThrow(() -> new IllegalArgumentException("No existe el curso."));
		Estudiante e = new Estudiante();
		e.setRut(req.rut());
		e.setNombres(req.nombres());
		e.setApellidoPaterno(req.apellidoPaterno());
		e.setApellidoMaterno(req.apellidoMaterno());
		e.setFechaNacimiento(req.fechaNacimiento());
		e.setEmail(req.email());
		e.setCurso(curso);
		return repositorio.save(e);
	}

	public List<Estudiante> obtenerEstudiantes() {
		return repositorio.findAll();
	}

	public List<Estudiante> obtenerEstudiantesPorCurso(Long cursoId) {
		return repositorio.findByCursoId(cursoId);
	}

	public Estudiante obtenerEstudianteID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public Estudiante obtenerEstudianteRut(String rut) {
		return repositorio.findByRut(rut).orElse(null);
	}

	public Estudiante modificarEstudiante(Estudiante e_mod) {
		Estudiante e = repositorio.findById(e_mod.getId()).orElse(null);
		if (e == null) {
			return null;
		}
		e.setRut(e_mod.getRut());
		e.setNombres(e_mod.getNombres());
		e.setApellidoPaterno(e_mod.getApellidoPaterno());
		e.setApellidoMaterno(e_mod.getApellidoMaterno());
		e.setFechaNacimiento(e_mod.getFechaNacimiento());
		e.setEmail(e_mod.getEmail());
		if (e_mod.getCurso() != null && e_mod.getCurso().getId() != null) {
			Curso curso = cursoRepositorio.findById(e_mod.getCurso().getId())
					.orElseThrow(() -> new IllegalArgumentException("No existe el curso."));
			e.setCurso(curso);
		}
		return repositorio.save(e);
	}

	public String borrarEstudiante(Long id) {
		repositorio.deleteById(id);
		return "Estudiante eliminado correctamente.";
	}
}
