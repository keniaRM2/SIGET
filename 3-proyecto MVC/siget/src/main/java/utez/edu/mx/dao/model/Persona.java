package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Formula;

@Data
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Size(max = 45)
    @NotNull
    @Column(name = "primer_apellido", nullable = false, length = 45)
    private String primerApellido;

    @Size(max = 45)
    @Column(name = "segundo_apellido", length = 45)
    private String segundoApellido;

    @Formula(value ="CONCAT(nombre, ' ', primer_apellido, ' ', IFNULL(segundo_apellido, ''))")
    private String nombreCompleto;

    @OneToOne(mappedBy = "persona", cascade = {CascadeType.ALL})
    private Usuario usuario;


    @OneToOne(mappedBy = "persona", cascade = {CascadeType.ALL})
    private Empleado empleado;
    public Persona() {
    }

    public Persona(String nombre, String primerApellido, String segundoApellido) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }
}