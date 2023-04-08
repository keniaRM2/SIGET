package utez.edu.mx.core.bean;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class CitaBean {
    
    private Integer id;
    private Timestamp fechaCita;
    private Timestamp horaInicio;
    private Timestamp horaFin;
    private Timestamp fechaRegistro;
    private AlumnoBean alumno;
    private ServicioBean servicio;
    private EstadoBean estado;
    private VentanillaBean ventanilla;
    private EmpleadoBean empleado;

}