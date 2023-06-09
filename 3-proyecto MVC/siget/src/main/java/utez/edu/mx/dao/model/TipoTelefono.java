package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_telefono", indexes = {
        @Index(name = "nombre_UNIQUE", columnList = "nombre", unique = true)
})
public class TipoTelefono {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_telefono", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    public TipoTelefono() {
    }

    public TipoTelefono(String nombre) {
        this.nombre = nombre;
    }

}