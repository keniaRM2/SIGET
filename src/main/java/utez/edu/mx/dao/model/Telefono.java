package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "telefono", indexes = {
        @Index(name = "fk_telefono_persona1_idx", columnList = "persona_id"),
        @Index(name = "fk_telefono_tipo_telefono1_idx", columnList = "tipo_telefono_id")
})
public class Telefono {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefono", nullable = false)
    private Integer id;

    @Size(max = 15)
    @NotNull
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_telefono_id", nullable = false)
    private TipoTelefono tipoTelefono;

}