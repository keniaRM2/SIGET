package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Dia;

public interface DiaRepository extends JpaRepository<Dia, Integer> {
    Dia findByNombre(String nombre);

    Dia findByNombreIgnoreCase(String nombre);
}