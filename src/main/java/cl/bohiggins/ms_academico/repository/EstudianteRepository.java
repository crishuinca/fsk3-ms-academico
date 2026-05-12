package cl.bohiggins.ms_academico.repository;

import cl.bohiggins.ms_academico.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

	List<Estudiante> findByCursoId(Long cursoId);

	Optional<Estudiante> findByRut(String rut);
}
