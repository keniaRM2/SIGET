package utez.edu.mx.core.bean;


import java.sql.Timestamp;
import lombok.Data;

@Data
public class BitacoraAccesoBean {

    private Integer id;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

}