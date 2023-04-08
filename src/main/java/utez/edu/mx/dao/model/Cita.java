package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;


import lombok.Data;

@Data
@Entity
@Table(name = "cita", indexes = {
        @Index(name = "fk_cita_estado1_idx", columnList = "estado_id"),
        @Index(name = "fk_cita_servicio1_idx", columnList = "servicio_id"),
        @Index(name = "fk_cita_alumno1_idx", columnList = "alumno_id"),
        @Index(name = "fk_cita_empleado1_idx", columnList = "empleado_id"),
        @Index(name = "fk_cita_ventanilla1_idx", columnList = "ventanilla_id")
})
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fecha_cita", nullable = false)
    private Timestamp fechaCita;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Timestamp horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private Timestamp horaFin;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private Timestamp fechaRegistro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ventanilla_id")
    private Ventanilla ventanilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

}