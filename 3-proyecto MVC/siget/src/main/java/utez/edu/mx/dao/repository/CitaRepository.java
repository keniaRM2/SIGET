package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
}