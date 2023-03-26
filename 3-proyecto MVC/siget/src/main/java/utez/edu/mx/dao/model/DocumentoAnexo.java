package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "documento_anexo", indexes = {
        @Index(name = "fk_documento_anexo_cita1_idx", columnList = "cita_id"),
        @Index(name = "fk_documento_anexo_documento1_idx", columnList = "documento_id")
})
public class DocumentoAnexo {
    @Id
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getDocumentoAnexo() {
        return documentoAnexo;
    }

    public void setDocumentoAnexo(byte[] documentoAnexo) {
        this.documentoAnexo = documentoAnexo;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

}