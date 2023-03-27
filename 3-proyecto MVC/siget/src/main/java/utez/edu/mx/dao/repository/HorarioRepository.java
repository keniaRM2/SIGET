package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {
}