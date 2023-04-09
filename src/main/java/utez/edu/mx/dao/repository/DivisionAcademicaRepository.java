package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.DivisionAcademica;

public interface DivisionAcademicaRepository extends JpaRepository<DivisionAcademica, Integer> {
    DivisionAcademica findByNombre(String nombre);
}