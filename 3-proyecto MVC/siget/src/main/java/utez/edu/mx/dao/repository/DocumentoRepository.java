package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Documento;
import utez.edu.mx.dao.model.Servicio;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
    Documento findByNombreIgnoreCaseAndServicio(String nombre, Servicio servicio);

    List<Documento> findAllByServicio(Servicio servicio);

    List<Documento> findAllByServicioAndIdNotIn(Servicio servicio, List<Integer> idsDocumentos);
}