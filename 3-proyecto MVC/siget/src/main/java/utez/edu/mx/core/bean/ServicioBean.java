package utez.edu.mx.core.bean;

import lombok.Data;

import java.util.List;

@Data
public class ServicioBean {


    private Integer id;
    private String nombre;
    private String descripcion;
    private Double costo;
    private Integer estatus;
    private List<DocumentoBean> documentos;


}