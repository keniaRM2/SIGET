package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "configuracion", indexes = {
        @Index(name = "key_UNIQUE", columnList = "key", unique = true)
})
public class Configuracion {
    @Id
    @Column(name = "id_configuracion", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "`key`", nullable = false, length = 45)
    private String key;

    @Size(max = 255)
    @Column(name = "value")
    private String value;

    @Column(name = "imagen")
    private byte[] imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}