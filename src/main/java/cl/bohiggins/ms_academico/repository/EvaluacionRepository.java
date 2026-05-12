package cl.bohiggins.ms_academico.repository;

import cl.bohiggins.ms_academico.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

	List<Evaluacion> findByCursoId(Long cursoId);
}
