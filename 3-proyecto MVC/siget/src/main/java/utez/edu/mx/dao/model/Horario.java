package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "horario", indexes = {
        @Index(name = "fk_horario_dia1_idx", columnList = "dia_id"),
        @Index(name = "fk_horario_ventanilla1_idx", columnList = "ventanilla_id"),
        @Index(name = "fk_horario_empleado1_idx", columnList = "empleado_id")
})
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario", nullable = false)
    private Integer id;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La hora de inicio no puede ser nula")
    @NotEmpty(message = "La hora de inicio no puede estar vacia")
    @Column(name = "hora_inicio", nullable = false)
    private Timestamp horaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La hora de fin no puede ser nula")
    @NotEmpty(message = "La hora de fin no puede estar vacia")
    @Column(name = "hora_fin", nullable = false)
    private Timestamp horaFin;

    @NotNull(message = "El dia no puede ser nulo")
    @NotEmpty(message = "El dia no puede estar vacio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dia_id", nullable = false)
    private Dia dia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ventanilla_id", nullable = false)
    private Ventanilla ventanilla;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

}