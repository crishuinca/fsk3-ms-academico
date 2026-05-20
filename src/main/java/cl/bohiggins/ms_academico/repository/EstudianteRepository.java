package cl.bohiggins.ms_academico.repository;

import cl.bohiggins.ms_academico.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	List<Estudiante> findByCursoId(Long cursoId);

	Optional<Estudiante> findByRut(String rut);

	@Query("SELECT COALESCE(MAX(e.id), 0) + 1 FROM Estudiante e")
	Long calcularProximoId();
}
