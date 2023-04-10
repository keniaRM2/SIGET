package utez.edu.mx.core.bean;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import utez.edu.mx.dao.model.DocumentoAnexo;

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
    private List<DocumentoAnexoBean> documentos;

    public CitaBean() {
    }

    public CitaBean(Timestamp horaInicio, Timestamp horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}