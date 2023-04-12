package utez.edu.mx.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.dao.model.ComprobantePago;
import utez.edu.mx.dao.model.Pago;

public interface ComprobantePagoRepository extends JpaRepository<ComprobantePago, Integer> {
    ComprobantePago findByPago(Pago pago);
}