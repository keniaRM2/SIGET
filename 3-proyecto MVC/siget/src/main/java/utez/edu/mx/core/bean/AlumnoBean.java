package utez.edu.mx.core.bean;

import lombok.Data;

@Data
public class AlumnoBean {

    private Integer id;
    private String matricula;
    private PersonaBean persona;
    private CarreraBean carrera;
}