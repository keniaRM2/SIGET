package utez.edu.mx.core.bean;


import lombok.Data;

@Data
public class CarreraBean {

    private Integer id;
    private String nombre;
    private DivisionAcademicaBean divisionAcademica;

}