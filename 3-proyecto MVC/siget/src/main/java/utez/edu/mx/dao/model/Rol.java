package utez.edu.mx.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column
    private String nombre;
//    @ManyToMany(mappedBy = "roles")
//    private List<Usuario> users = new ArrayList<>();

    public Rol(String nombre) {
        this.nombre = nombre;
    }
}
