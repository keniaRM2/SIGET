package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Pago findByCita(Cita cita);
}