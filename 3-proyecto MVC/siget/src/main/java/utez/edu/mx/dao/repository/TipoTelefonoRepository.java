package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.TipoTelefono;

public interface TipoTelefonoRepository extends JpaRepository<TipoTelefono, Integer> {
    TipoTelefono findByNombre(String nombre);
}