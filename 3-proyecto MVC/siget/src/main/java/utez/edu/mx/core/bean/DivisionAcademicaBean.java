package utez.edu.mx.core.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DivisionAcademicaBean {

    private Integer id;
    private String nombre;

}