package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "bitacora_acceso", indexes = {
        @Index(name = "fk_bitacora_acceso_usuario1_idx", columnList = "usuario_id")
})
public class BitacoraAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora_acceso", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Timestamp fechaInicio;

    @Column(name = "fecha_fin")
    private Timestamp fechaFin;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}