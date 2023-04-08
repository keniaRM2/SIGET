package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    Servicio findByNombre(String nombre);
    Servicio findByNombreAndIdIsNot(String nombre,Integer id);
}