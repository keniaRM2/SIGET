package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "alumno", indexes = {
        @Index(name = "fk_alumno_carrera1_idx", columnList = "carrera_id"),
        @Index(name = "persona_id_persona_UNIQUE", columnList = "persona_id", unique = true),
        @Index(name = "matricula_UNIQUE", columnList = "matricula", unique = true)
})
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno", nullable = false)
    private Integer id;

    @Size(max = 12)
    @NotEmpty(message = "La matrícula no puede estar vacía")
    @NotBlank(message = "La matrícula no puede contener solo espacios en blanco")
    @Column(name = "matricula", nullable = false, length = 12)
    private String matricula;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;


}