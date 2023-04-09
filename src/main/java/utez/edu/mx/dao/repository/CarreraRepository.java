package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Carrera;
import utez.edu.mx.dao.model.DivisionAcademica;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
    Carrera findByNombreAndDivisionAcademica(String nombre, DivisionAcademica divisionAcademica);
}