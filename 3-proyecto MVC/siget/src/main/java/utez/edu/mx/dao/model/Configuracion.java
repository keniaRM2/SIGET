package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Data;

@Data
@Entity
@Table(name = "configuracion", indexes = {
        @Index(name = "key_UNIQUE", columnList = "key", unique = true)
})
public class Configuracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}