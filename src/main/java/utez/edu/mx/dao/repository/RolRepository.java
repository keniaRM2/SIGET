package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByAuthority(String authority);
}