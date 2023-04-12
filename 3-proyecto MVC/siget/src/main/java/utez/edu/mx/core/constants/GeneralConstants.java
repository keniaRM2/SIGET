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

    public static final String  ESTATUS_GRATUITO = "Gratuito";
    public static final String  ESTATUS_CON_COSTO = "Costo";

    public static final String TIPO_ESTADO_PAGO = "Pago";
    public static final String TIPO_ESTADO_CITA = "Cita";
    public static final BCryptPasswordEncoder CODIFICAROR =  new BCryptPasswordEncoder();
    public static final String ESTADO_CITA_CANCELADA = "Cancelada";
    public static final String ESTADO_CITA_ACEPTADA ="Aceptada" ;
    public static final String ESTADO_CITA_PROCESO = "En Proceso";
    public static final String ESTADO_CITA_RECIBIDA = "Recibida";
    public static final String ESTADO_CITA_NO_RECIBIDA = "No Recibida";
    public static final String ESTADO_PAGO_RECHAZADO = "Rechazado";
    public static final String ESTADO_PAGO_ACEPTADO = "Aceptado";


    //PayPal
    public static final String CLIENT_ID = "AcrqeULL1pqvvI30tH1nVpPT9OYAP87sdibq-lqMeBR3LycIHjzUCUb0Hdcn3m0GPsLz2uDr4SLA_6fx";
    public static final String CLIENT_SECRET = "EB7D_dEMsVhv5tEmDEPTGRro3YIiEc0bO1_u3pmNNUy16VgAHT6-3q1YsViNLlf0-Q072MbaumhU9Eu7";
    public static final String MODE = "sandbox";
    public static final String AUTHORIZE = "authorize";
    public static final String PAYPAL = "paypal";
    public static final String MXN = "MXN";
    public static final String CANTIDAD_PAGO = "1";
    public static final String URL_APROBADO = "approval_url";
    public static final String ID_PAGO_PAYPAL = "paymentId";
    public static final String ID_EMISIOR_PAYPAL = "PayerID";
    public static final String PDF_FORMATO = ".pdf";
    public static final String COMPROBANTE_PAGO = "COMPROBANTE DE PAGO";

    private GeneralConstants() {
    }

}
