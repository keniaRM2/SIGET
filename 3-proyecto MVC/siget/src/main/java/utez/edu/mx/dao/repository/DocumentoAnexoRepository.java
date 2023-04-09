package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.DocumentoAnexo;

import java.util.List;

public interface DocumentoAnexoRepository extends JpaRepository<DocumentoAnexo, Integer> {
    List<DocumentoAnexo> findAllByCita(Cita cita);
}