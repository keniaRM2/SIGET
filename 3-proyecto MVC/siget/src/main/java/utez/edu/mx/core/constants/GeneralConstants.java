package utez.edu.mx.core.constants;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneralConstants {
    public static final String ROL_ADMIN = "ROLE_ADMIN";
    public static final String ROL_EMPLEADO = "ROLE_EMPLEADO";
    public static final String ROL_ALUMNO = "ROLE_ALUMNO";
    public static final String TIPO_SERVICIO_GRATUITO = "Gratuito";
    public static final String TIPO_SERVICIO_COSTO = "Costo";

    public static final Integer  ESTATUS_ACTIVO = 1;
    public static final Integer  ESTATUS_INACTIVO = 0;
    public static final String TIPO_ESTADO_PAGO = "Pago";
    public static final String TIPO_ESTADO_CITA = "Cita";

    public static final String ESTADO_CITA_ACEPTADA = "Aceptada";
    public static final String ESTADO_CITA_CANCELADA= "Cancelada";
    public static final String ESTADO_CITA_PROCESO= "En proceso";
    public static final String ESTADO_CITA_RECIBIDA= "Recibida";
    public static final String ESTADO_CITA_NO_RECIBIDA= "No Recibida";
    public static final String ESTADO_PAGO_PAGADO="Pagado";
    public static final String ESTADO_PAGO_RECHAZADO="Rechazado";
    public static final BCryptPasswordEncoder CODIFICAROR =  new BCryptPasswordEncoder();

    private GeneralConstants() {
    }

}
