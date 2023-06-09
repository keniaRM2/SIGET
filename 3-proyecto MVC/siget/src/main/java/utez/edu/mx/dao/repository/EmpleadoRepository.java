package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByNumeroEmpleado(String numeroEmpleado);
    Empleado findByNumeroEmpleadoAndIdIsNot(String numeroEmpleado, Integer id);

  

}