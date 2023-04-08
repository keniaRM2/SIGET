package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.TipoEstado;

public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Integer> {
    TipoEstado findByNombre(String nombre);
}