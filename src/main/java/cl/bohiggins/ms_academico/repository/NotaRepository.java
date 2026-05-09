package cl.bohiggins.ms_academico.repository;

import cl.bohiggins.ms_academico.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {

	List<Nota> findByEstudianteId(Long estudianteId);
}
