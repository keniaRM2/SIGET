package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
}