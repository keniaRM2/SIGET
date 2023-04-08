package utez.edu.mx.core.bean;

import lombok.Data;

import java.util.List;

@Data
public class EmpleadoBean {

    private Integer id;
    private String numeroEmpleado;
    private PersonaBean persona;
    private List<HorarioBean> horarios;

}