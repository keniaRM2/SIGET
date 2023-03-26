package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "telefono", indexes = {
        @Index(name = "fk_telefono_persona1_idx", columnList = "persona_id"),
        @Index(name = "fk_telefono_tipo_telefono1_idx", columnList = "tipo_telefono_id")
})
public class Telefono {
    @Id
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoTelefono getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(TipoTelefono tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

}