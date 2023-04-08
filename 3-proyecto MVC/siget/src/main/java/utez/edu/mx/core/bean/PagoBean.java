package utez.edu.mx.core.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagoBean {

    private Integer id;
    private BigDecimal monto;
    private CitaBean cita;
    private EstadoBean estado;

}