package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


import lombok.Data;

@Data
@Entity
@Table(name = "servicio", indexes = {
        @Index(name = "nombre_UNIQUE", columnList = "nombre", unique = true),
        @Index(name = "fk_servicio_tipo_servicio1_idx", columnList = "tipo_servicio_id")
})
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Size(max = 500)
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "costo", precision = 19, scale = 4)
    private Double costo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_servicio_id", nullable = false)
    private TipoServicio tipoServicio;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Integer estatus;

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, Double costo, TipoServicio tipoServicio, Integer estatus) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.tipoServicio = tipoServicio;
        this.estatus = estatus;
    }
}