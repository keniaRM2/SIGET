package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Empleado;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findAllByEmpleado(Empleado empleado);
}