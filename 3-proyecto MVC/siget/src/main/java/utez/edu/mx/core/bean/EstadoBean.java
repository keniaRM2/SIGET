package utez.edu.mx.core.bean;

import lombok.Data;

@Data
public class EstadoBean {

    private Integer id;
    private String nombre;
    private TipoEstadoBean tipoEstado;
    private String color;

}