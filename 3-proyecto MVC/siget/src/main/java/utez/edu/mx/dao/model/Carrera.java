package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "carrera", indexes = {
        @Index(name = "fk_carrera_division_academica1_idx", columnList = "division_academica_id"),
        @Index(name = "nombre_UNIQUE", columnList = "nombre", unique = true)
})
public class Carrera {
    @Id
    @Column(name = "id_carrera", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "division_academica_id", nullable = false)
    private DivisionAcademica divisionAcademica;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DivisionAcademica getDivisionAcademica() {
        return divisionAcademica;
    }

    public void setDivisionAcademica(DivisionAcademica divisionAcademica) {
        this.divisionAcademica = divisionAcademica;
    }

}