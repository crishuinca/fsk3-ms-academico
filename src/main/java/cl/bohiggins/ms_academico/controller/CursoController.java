package cl.bohiggins.ms_academico.controller;

import cl.bohiggins.ms_academico.entity.Curso;
import cl.bohiggins.ms_academico.service.CursoService;
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
@Tag(name = "Cursos V1", description = "Nivel, letra y año (ej. 5° Básico A, 2026)")
public class CursoController {

	@Autowired
	private CursoService servicio;

	@Operation(summary = "Crear curso", description = "Registra un curso en el sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Curso creado") })
	@PostMapping("/addCurso")
	public Curso c_guardarCurso(@Valid @RequestBody Curso c) {
		c.setId(null);
		return servicio.guardarCurso(c);
	}

	@Operation(summary = "Crear varios cursos")
	@PostMapping("/addCursos")
	public List<Curso> c_guardarCursos(@RequestBody List<Curso> lista) {
		lista.forEach(x -> x.setId(null));
		return servicio.guardarCursos(lista);
	}

	@Operation(summary = "Listar cursos")
	@GetMapping("/cursos")
	public List<Curso> c_obtenerCursos() {
		return servicio.obtenerCursos();
	}

	@Operation(summary = "Obtener curso por ID")
	@GetMapping("/cursoByID/{id}")
	public Curso c_obtenerCursoID(@PathVariable Long id) {
		return servicio.obtenerCursoID(id);
	}

	@Operation(summary = "Modificar curso")
	@PutMapping("/modificarCurso")
	public Curso c_modificarCurso(@Valid @RequestBody Curso c) {
		return servicio.modificarCurso(c);
	}

	@Operation(summary = "Eliminar curso")
	@DeleteMapping("/eliminarCurso/{id}")
	public String c_borrarCurso(@PathVariable Long id) {
		return servicio.borrarCurso(id);
	}
}
