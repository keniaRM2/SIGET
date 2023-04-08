package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {
}