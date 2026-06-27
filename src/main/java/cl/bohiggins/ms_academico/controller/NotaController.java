package cl.bohiggins.ms_academico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.bohiggins.ms_academico.dto.NotaCreateRequest;
import cl.bohiggins.ms_academico.entity.Nota;
import cl.bohiggins.ms_academico.service.NotaService;
import cl.bohiggins.ms_academico.web.RecursoHttp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Notas V1", description = "Calificaciones 1.0 a 7.0 ")
public class NotaController {

	@Autowired
	private NotaService servicio;

	@Operation(summary = "Registrar nota")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Nota creada") })
	@PostMapping("/addNota")
	@ResponseStatus(HttpStatus.CREATED)
	public Nota c_guardarNota(@Valid @RequestBody NotaCreateRequest req) {
		return servicio.guardarNota(req);
	}

	@Operation(summary = "Listar notas")
	@GetMapping("/notas")
	public List<Nota> c_obtenerNotas() {
		return servicio.obtenerNotas();
	}

	@Operation(summary = "Listar notas de un estudiante")
	@GetMapping("/notasPorEstudiante/{estudianteId}")
	public List<Nota> c_obtenerNotasPorEstudiante(@PathVariable Long estudianteId) {
		return servicio.obtenerNotasPorEstudiante(estudianteId);
	}

	@Operation(summary = "Obtener nota por ID")
	@GetMapping("/notaByID/{id}")
	public Nota c_obtenerNotaID(@PathVariable Long id) {
		return RecursoHttp.requerir(servicio.obtenerNotaID(id), "Nota no encontrada.");
	}

	@Operation(summary = "Modificar nota")
	@PutMapping("/modificarNota")
	public Nota c_modificarNota(@RequestBody Nota n) {
		return RecursoHttp.requerir(servicio.modificarNota(n), "Nota no encontrada.");
	}

	@Operation(summary = "Eliminar nota")
	@DeleteMapping("/eliminarNota/{id}")
	public String c_borrarNota(@PathVariable Long id) {
		return servicio.borrarNota(id);
	}
}
