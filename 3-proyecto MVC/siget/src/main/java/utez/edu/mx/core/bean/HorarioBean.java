package utez.edu.mx.core.bean;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class HorarioBean {

    private Integer id;
    private Timestamp horaInicio;
    private Timestamp horaFin;
    private DiaBean dia;
    private VentanillaBean ventanilla;
    private EmpleadoBean empleado;

}