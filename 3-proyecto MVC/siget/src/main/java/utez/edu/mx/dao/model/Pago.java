package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.Data;

@Data
@Entity
@Table(name = "pago", indexes = {
        @Index(name = "fk_pago_estado1_idx", columnList = "estado_id"),
        @Index(name = "fk_pago_cita1_idx", columnList = "cita_id")
})
public class Pago {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "monto", nullable = false, precision = 19, scale = 4)
    private BigDecimal monto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

}