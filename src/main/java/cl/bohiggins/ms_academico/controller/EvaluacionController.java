package cl.bohiggins.ms_academico.controller;

import cl.bohiggins.ms_academico.dto.EvaluacionCreateRequest;
import cl.bohiggins.ms_academico.entity.Evaluacion;
import cl.bohiggins.ms_academico.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Evaluaciones V1", description = "Pruebas, controles, trabajos (ponderación en %)")
public class EvaluacionController {

	@Autowired
	private EvaluacionService servicio;

	@Operation(summary = "Crear evaluación")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Evaluación creada") })
	@PostMapping("/addEvaluacion")
	public Evaluacion c_guardarEvaluacion(@Valid @RequestBody EvaluacionCreateRequest req) {
		return servicio.guardarEvaluacion(req);
	}

	@Operation(summary = "Listar evaluaciones")
	@GetMapping("/evaluaciones")
	public List<Evaluacion> c_obtenerEvaluaciones() {
		return servicio.obtenerEvaluaciones();
	}

	@Operation(summary = "Listar evaluaciones de un curso")
	@GetMapping("/evaluacionesPorCurso/{cursoId}")
	public List<Evaluacion> c_obtenerEvaluacionesPorCurso(@PathVariable Long cursoId) {
		return servicio.obtenerEvaluacionesPorCurso(cursoId);
	}

	@Operation(summary = "Obtener evaluación por ID")
	@GetMapping("/evaluacionByID/{id}")
	public Evaluacion c_obtenerEvaluacionID(@PathVariable Long id) {
		return servicio.obtenerEvaluacionID(id);
	}

	@Operation(summary = "Modificar evaluación", description = "Puede incluir asignatura y curso con id")
	@PutMapping("/modificarEvaluacion")
	public Evaluacion c_modificarEvaluacion(@RequestBody Evaluacion ev) {
		return servicio.modificarEvaluacion(ev);
	}

	@Operation(summary = "Eliminar evaluación")
	@DeleteMapping("/eliminarEvaluacion/{id}")
	public String c_borrarEvaluacion(@PathVariable Long id) {
		return servicio.borrarEvaluacion(id);
	}
}
