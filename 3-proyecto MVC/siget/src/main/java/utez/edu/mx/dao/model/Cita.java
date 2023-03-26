package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.sql.Timestamp;
import java.sql.Timestamp;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Timestamp fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Ventanilla getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(Ventanilla ventanilla) {
        this.ventanilla = ventanilla;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}