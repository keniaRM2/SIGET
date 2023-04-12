package utez.edu.mx.dao.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario", indexes = {
        @Index(name = "fk_usuario_persona1_idx", columnList = "persona_id"),
        @Index(name = "username_UNIQUE", columnList = "username", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "persona_id_UNIQUE", columnNames = {"persona_id"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotEmpty(message = "El correo no puede estar vacío")
    @NotBlank(message = "El correo no puede contener solo espacios en blanco")
    @Email(message = "El correo no es válido")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "El correo no es válido")
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Integer enabled;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;


    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    public Usuario() {
    }

    public Usuario(String username, String password, Integer enabled, Rol rol, Persona persona) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.rol = rol;
        this.persona = persona;
    }
}
