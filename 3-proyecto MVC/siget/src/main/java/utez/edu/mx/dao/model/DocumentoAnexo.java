package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "documento_anexo", indexes = {
        @Index(name = "fk_documento_anexo_cita1_idx", columnList = "cita_id"),
        @Index(name = "fk_documento_anexo_documento1_idx", columnList = "documento_id")
})
public class DocumentoAnexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_anexo", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "documento_anexo", nullable = false)
    private byte[] documentoAnexo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;


}