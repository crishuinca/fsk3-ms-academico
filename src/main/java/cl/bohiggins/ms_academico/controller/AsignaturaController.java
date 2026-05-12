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

import cl.bohiggins.ms_academico.entity.Asignatura;
import cl.bohiggins.ms_academico.service.AsignaturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Asignaturas V1", description = "Materias del colegio")
public class AsignaturaController {

	@Autowired
	private AsignaturaService servicio;

	@Operation(summary = "Crear asignatura")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Asignatura creada") })
	@PostMapping("/addAsignatura")
	public Asignatura c_guardarAsignatura(@Valid @RequestBody Asignatura a) {
		a.setId(null);
		return servicio.guardarAsignatura(a);
	}

	@Operation(summary = "Crear varias asignaturas")
	@PostMapping("/addAsignaturas")
	public List<Asignatura> c_guardarAsignaturas(@RequestBody List<Asignatura> lista) {
		lista.forEach(x -> x.setId(null));
		return servicio.guardarAsignaturas(lista);
	}

	@Operation(summary = "Listar asignaturas")
	@GetMapping("/asignaturas")
	public List<Asignatura> c_obtenerAsignaturas() {
		return servicio.obtenerAsignaturas();
	}

	@Operation(summary = "Obtener asignatura por ID")
	@GetMapping("/asignaturaByID/{id}")
	public Asignatura c_obtenerAsignaturaID(@PathVariable Long id) {
		return servicio.obtenerAsignaturaID(id);
	}

	@Operation(summary = "Modificar asignatura")
	@PutMapping("/modificarAsignatura")
	public Asignatura c_modificarAsignatura(@Valid @RequestBody Asignatura a) {
		return servicio.modificarAsignatura(a);
	}

	@Operation(summary = "Eliminar asignatura")
	@DeleteMapping("/eliminarAsignatura/{id}")
	public String c_borrarAsignatura(@PathVariable Long id) {
		return servicio.borrarAsignatura(id);
	}
}
