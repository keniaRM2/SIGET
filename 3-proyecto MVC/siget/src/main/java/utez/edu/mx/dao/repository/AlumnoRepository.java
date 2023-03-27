package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
}