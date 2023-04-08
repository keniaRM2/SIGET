package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.BitacoraAcceso;
import utez.edu.mx.dao.model.Usuario;

import java.util.List;

public interface BitacoraAccesoRepository extends JpaRepository<BitacoraAcceso, Integer> {

    List<BitacoraAcceso> findAllByUsuarioAndFechaFinIsNullOrderByFechaInicioDesc(Usuario usuario);
}