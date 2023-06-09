package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    Alumno findByMatricula(Object object);
    Alumno findByMatriculaAndIdIsNot(String matriculaAlumno, Integer id);
}