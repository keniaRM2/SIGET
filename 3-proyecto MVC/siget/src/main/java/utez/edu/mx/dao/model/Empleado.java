package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "empleado", indexes = {
        @Index(name = "fk_empleado_persona1_idx", columnList = "persona_id"),
        @Index(name = "numero_empleado_UNIQUE", columnList = "numero_empleado", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "persona_id_UNIQUE", columnNames = {"persona_id"})
})
public class Empleado {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
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

}