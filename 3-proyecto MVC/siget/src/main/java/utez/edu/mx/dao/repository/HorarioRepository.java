package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.*;

import java.sql.Timestamp;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    List<Horario> findAllByVentanillaAndDiaAndHoraInicioBetween(
            Ventanilla ventanilla,
            Dia dia,
            Timestamp horaInicio,
            Timestamp horaFin);


    Horario findAllByVentanillaAndEmpleadoAndDiaAndHoraInicioBetween(
            Ventanilla ventanilla,
            Empleado empleado,
            Dia dia,
            Timestamp horaInicio,
            Timestamp horaFin);



    List<Horario> findAllByDiaAndEmpleadoInOrderByHoraInicio(Dia dia, List<Empleado> empleados);

    List<Horario> findAllByIdInAndHoraInicioGreaterThanEqualAndHoraFinLessThan(List<Integer> ids, Timestamp horaInicio, Timestamp horaFin);

    List <Horario> findAllByEmpleado (Empleado empleado);
}

