package cl.bohiggins.ms_academico.repository;

import cl.bohiggins.ms_academico.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
