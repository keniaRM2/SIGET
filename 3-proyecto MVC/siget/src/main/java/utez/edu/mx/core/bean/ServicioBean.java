package utez.edu.mx.core.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


import lombok.Data;

@Data
public class ServicioBean {


    private Integer id;
    private String nombre;
    private String descripcion;
    private Double costo;
    private Integer estatus;


}