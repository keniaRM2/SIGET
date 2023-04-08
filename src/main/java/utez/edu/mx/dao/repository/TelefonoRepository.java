package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Telefono;

public interface TelefonoRepository extends JpaRepository<Telefono, Integer> {
}