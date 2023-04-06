package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "estado", indexes = {
        @Index(name = "fk_estado_tipo_estado1_idx", columnList = "tipo_estado_id")
})
public class Estado {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_estado_id", nullable = false)
    private TipoEstado tipoEstado;

    @Size(max = 20)
    @Column(name = "color", length = 20)
    private String color;

    public Estado() {
    }

    public Estado(String nombre, TipoEstado tipoEstado, String color) {
        this.nombre = nombre;
        this.tipoEstado = tipoEstado;
        this.color = color;
    }
}