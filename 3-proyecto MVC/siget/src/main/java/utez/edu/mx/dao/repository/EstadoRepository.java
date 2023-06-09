package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Estado;
import utez.edu.mx.dao.model.TipoEstado;

import java.util.List;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Estado findByNombreAndTipoEstado(String nombreEstado, TipoEstado tipoEstado);

    List<Estado> findAllByTipoEstado(TipoEstado tipoEstado);
}