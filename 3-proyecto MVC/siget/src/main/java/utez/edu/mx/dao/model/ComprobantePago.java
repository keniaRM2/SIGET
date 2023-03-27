package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import lombok.Data;

@Data
@Entity
@Table(name = "comprobante_pago", indexes = {
        @Index(name = "fk_comprobante_pago_pago1_idx", columnList = "pago_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "pago_id_pago_UNIQUE", columnNames = {"pago_id"})
})
public class ComprobantePago {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante_pago", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "comprobante", nullable = false)
    private byte[] comprobante;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;

}