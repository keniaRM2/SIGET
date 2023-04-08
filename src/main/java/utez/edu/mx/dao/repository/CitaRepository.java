package utez.edu.mx.dao.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.dao.model.Estado;
import utez.edu.mx.dao.model.Ventanilla;

import java.sql.Timestamp;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findAllByEmpleado(Empleado empleado);
         List<Cita> findAllByFechaCitaAndHoraInicioBetweenOrHoraFinBetweenAndEstadoIsNotAndVentanilla(
                 @NotNull Timestamp fechaCita,
                 @NotNull Timestamp horaInicio,
                 @NotNull Timestamp horaInicio2,
                 @NotNull Timestamp horaFin,
                 @NotNull Timestamp horaFin2,
                 @NotNull Estado estado,
                 @NotNull Ventanilla ventanilla

                 );
    }
