package cl.bohiggins.ms_academico.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.bohiggins.ms_academico.dto.NotaCreateRequest;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.entity.Evaluacion;
import cl.bohiggins.ms_academico.entity.Nota;
import cl.bohiggins.ms_academico.repository.EstudianteRepository;
import cl.bohiggins.ms_academico.repository.EvaluacionRepository;
import cl.bohiggins.ms_academico.repository.NotaRepository;

@Service
public class NotaService {

	private static final BigDecimal MIN = new BigDecimal("1.0");
	private static final BigDecimal MAX = new BigDecimal("7.0");

	@Autowired
	private NotaRepository repositorio;

	@Autowired
	private EstudianteRepository estudianteRepositorio;

	@Autowired
	private EvaluacionRepository evaluacionRepositorio;

	public Nota guardarNota(NotaCreateRequest req) {
		Estudiante est = estudianteRepositorio.findById(req.estudianteId())
				.orElseThrow(() -> new IllegalArgumentException("No existe el estudiante."));
		Evaluacion ev = evaluacionRepositorio.findById(req.evaluacionId())
				.orElseThrow(() -> new IllegalArgumentException("No existe la evaluacion."));
		Nota n = new Nota();
		n.setValor(req.valor());
		n.setEstudiante(est);
		n.setEvaluacion(ev);
		n.setObservacion(req.observacion());
		n.setFechaRegistro(LocalDateTime.now());
		return repositorio.save(n);
	}

	public List<Nota> obtenerNotas() {
		return repositorio.findAll();
	}

	public List<Nota> obtenerNotasPorEstudiante(Long estudianteId) {
		return repositorio.findByEstudianteId(estudianteId);
	}

	public Nota obtenerNotaID(Long id) {
		return repositorio.findById(id).orElse(null);
	}

	public Nota modificarNota(Nota n_mod) {
		Nota n = repositorio.findById(n_mod.getId()).orElse(null);
		if (n == null) {
			return null;
		}
		validarRangoNota(n_mod.getValor());
		n.setValor(n_mod.getValor());
		n.setObservacion(n_mod.getObservacion());
		return repositorio.save(n);
	}

	private void validarRangoNota(BigDecimal v) {
		if (v == null || v.compareTo(MIN) < 0 || v.compareTo(MAX) > 0) {
			throw new IllegalArgumentException("La nota debe ser entre 1.0 y 7.0.");
		}
	}

	public String borrarNota(Long id) {
		repositorio.deleteById(id);
		return "Nota eliminada correctamente.";
	}
}
