package cl.bohiggins.ms_academico.controller;

import java.util.List;

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

import cl.bohiggins.ms_academico.dto.EstudianteCreateRequest;
import cl.bohiggins.ms_academico.entity.Estudiante;
import cl.bohiggins.ms_academico.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Estudiantes V1", description = "Alumnos con RUT y curso")
public class EstudianteController {

	@Autowired
	private EstudianteService servicio;

	@Operation(summary = "Registrar estudiante", description = "Crea un estudiante asociado a un curso existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Estudiante creado") })
	@PostMapping("/addEstudiante")
	public Estudiante c_guardarEstudiante(@Valid @RequestBody EstudianteCreateRequest req) {
		return servicio.guardarEstudiante(req);
	}

	@Operation(summary = "Listar todos los estudiantes")
	@GetMapping("/estudiantes")
	public List<Estudiante> c_obtenerEstudiantes() {
		return servicio.obtenerEstudiantes();
	}

	@Operation(summary = "Listar estudiantes de un curso")
	@GetMapping("/estudiantesPorCurso/{cursoId}")
	public List<Estudiante> c_obtenerEstudiantesPorCurso(@PathVariable Long cursoId) {
		return servicio.obtenerEstudiantesPorCurso(cursoId);
	}

	@Operation(summary = "Obtener estudiante por ID")
	@GetMapping("/estudianteByID/{id}")
	public Estudiante c_obtenerEstudianteID(@PathVariable Long id) {
		return servicio.obtenerEstudianteID(id);
	}

	@Operation(summary = "Obtener estudiante por RUT")
	@GetMapping("/estudianteByRut/{rut}")
	public Estudiante c_obtenerEstudianteRut(@PathVariable String rut) {
		return servicio.obtenerEstudianteRut(rut);
	}

	@Operation(summary = "Modificar estudiante", description = "Incluye curso con id si cambia de curso")
	@PutMapping("/modificarEstudiante")
	public Estudiante c_modificarEstudiante(@RequestBody Estudiante e) {
		return servicio.modificarEstudiante(e);
	}

	@Operation(summary = "Eliminar estudiante")
	@DeleteMapping("/eliminarEstudiante/{id}")
	public String c_borrarEstudiante(@PathVariable Long id) {
		return servicio.borrarEstudiante(id);
	}
}
