package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.TipoServicio;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {
    TipoServicio findByNombre(String nombre);
}