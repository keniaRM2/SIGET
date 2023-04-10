package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Dia;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.dao.model.Horario;
import utez.edu.mx.dao.model.Ventanilla;

import java.sql.Timestamp;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    List<Horario> findAllByVentanillaAndDiaAndHoraInicioBetween(
            Ventanilla ventanilla,
            Dia dia,
            Timestamp horaInicio,
            Timestamp horaFin);

    List<Horario> findAllByDiaAndEmpleadoInOrderByHoraInicio(Dia dia, List<Empleado> empleados);
}
