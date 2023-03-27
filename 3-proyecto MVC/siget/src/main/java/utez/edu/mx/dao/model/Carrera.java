package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "carrera", indexes = {
        @Index(name = "fk_carrera_division_academica1_idx", columnList = "division_academica_id"),
        @Index(name = "nombre_UNIQUE", columnList = "nombre", unique = true)
})
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}