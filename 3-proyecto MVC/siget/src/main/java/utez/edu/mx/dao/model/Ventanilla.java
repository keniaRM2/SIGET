package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "ventanilla", indexes = {
        @Index(name = "nombre_UNIQUE", columnList = "nombre", unique = true)
})
public class Ventanilla {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ventanilla", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    public Ventanilla() {
    }

    public Ventanilla(String nombre) {
        this.nombre = nombre;
    }
}