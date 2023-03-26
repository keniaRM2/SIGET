package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empleado", indexes = {
        @Index(name = "fk_empleado_persona1_idx", columnList = "persona_id"),
        @Index(name = "numero_empleado_UNIQUE", columnList = "numero_empleado", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "persona_id_UNIQUE", columnNames = {"persona_id"})
})
public class Empleado {
    @Id
    @Column(name = "id_empleado", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "numero_empleado", nullable = false, length = 20)
    private String numeroEmpleado;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}