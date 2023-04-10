package utez.edu.mx.core.constants;

import org.springframework.stereotype.Component;

@Component
public class PathConstants {

    public static final String ALL_SUBPATHS = "/**";
    public static final String LOGIN = "/login";
    public static final String LOGIN_ALL_SUBPATHS = LOGIN + ALL_SUBPATHS;
    public static final String ASSETS = "/assets";
    public static final String ASSETS_ALL_SUBPATHS = ASSETS + ALL_SUBPATHS;
    public static final String INDEX = "/";
    public static final String INDEX_NAME = "/index";
    public static final String INDEX_ALL_SUBPATHS = "/**";
    public static final String ERROR = "/error";
    public static final String ERROR_ALL_SUBPATHS = ERROR + ALL_SUBPATHS;

    //DOCUMENTO
    private static final String DOCUMENTO_SERVICIO = "/documento";
    public static final String LISTAR_DOCUMENTOS_SERVICIO = DOCUMENTO_SERVICIO + "/listarDocumentosPorServicio";
    // EMPLEADOS
    private static final String EMPLEADO_CONTROLLER = "/empleado";
    public static final String LISTAR_EMPLEADOS = EMPLEADO_CONTROLLER;
    public static final String REGISTRAR_EMPLEADO = EMPLEADO_CONTROLLER + "/registrar";
    public static final String EDITAR_EMPLEADO = EMPLEADO_CONTROLLER + "/editar";
    public static final String ACTUALIZAR_ESTATUS_EMPLEADO = EMPLEADO_CONTROLLER + "/actualizarEstatus";
    public static final String GUARDAR_EMPLEADO = EMPLEADO_CONTROLLER + "/guardar";
    public static final String INICIO_EMPLEADO = EMPLEADO_CONTROLLER + "/inicio";
    public static final String BANDEJA_CITAS = EMPLEADO_CONTROLLER + "/bandeja_citas";
    public static final String HORARIO_CITAS = EMPLEADO_CONTROLLER + "/horario_citas";

    //EMPLEADO HORARIO
    private static final String HORARIO_EMPLEADO_CONTROLLER = "/horario";
    public static final String LISTAR_HORARIOS_EMPLEADO = HORARIO_EMPLEADO_CONTROLLER;
    public static final String LISTAR_HORARIO_DIAS = HORARIO_EMPLEADO_CONTROLLER+"/listarDias";
    public static final String INICIO_HORARIO_EMPLEADO = HORARIO_EMPLEADO_CONTROLLER + "/inicio";



    // SERVICIO
    private static final String TIPOSERVICIO_CONTROLLER = "/tiposerv";
    private static final String SERVICIO_CONTROLLER = "/servicio";
    public static final String LISTAR_SERVICIOS = SERVICIO_CONTROLLER;
    public static final String EDITAR_SERVICIO = SERVICIO_CONTROLLER + "/editar";
    public static final String REGISTRAR_SERVICIO = SERVICIO_CONTROLLER + "/registrar";
    public static final String REMOVER_REMOVER_DOCUMENTO = SERVICIO_CONTROLLER + "/removerDocumento";

    public static final String ACTUALIZAR_ESTATUS_SERVICIO = SERVICIO_CONTROLLER + "/actualizarEstatus";
    public static final String GUARDAR_SERVICIO = SERVICIO_CONTROLLER + "/guardar";


    // CITA
    private static final String CITAS_CONTROLLER = "/cita";
    public static final String CALENDARIO_CITA_ADMIN = CITAS_CONTROLLER;
    public static final String CALENDARIO_CITA_EMPLEADO = CITAS_CONTROLLER+"/calendario";
    public static final String LISTAR_CITAS = CITAS_CONTROLLER + "/listarCitas";
    public static final String GUARDAR_CITA = CITAS_CONTROLLER + "/cita";
    public static final String REGISTRAR_CITA = CITAS_CONTROLLER + "/registrar";
    public static final String EDITAR_ESTADO_CITA = CITAS_CONTROLLER + "/editarEstadoCita";
    public static final String INFORMACION_CITA = CITAS_CONTROLLER + "/informacionCita";


    // ALUMNO
    private static final String ALUMNO_CONTROLLER = "/alumno";
    public static final String LISTAR_ALUMNOS = ALUMNO_CONTROLLER;
    public static final String REGISTRAR_ALUMNO = ALUMNO_CONTROLLER + "/registrar";
    public static final String EDITAR_ALUMNO = ALUMNO_CONTROLLER + "/editar";
    public static final String ACTUALIZAR_ESTATUS_ALUMNO = ALUMNO_CONTROLLER + "/actualizarEstatus";
    public static final String GUARDAR_ALUMNO = ALUMNO_CONTROLLER + "/guardar";
    public static final String INICIO_ALUMNO = ALUMNO_CONTROLLER + "/inicio_alumno";
    public static final String HISTORIAL_ALUMNO = ALUMNO_CONTROLLER + "/historial_alumno";
    public static final String INICIO_FECHA = ALUMNO_CONTROLLER + "/inicio_fecha";
    public static final String INICIO_PAGO = ALUMNO_CONTROLLER + "/inicio_pago";
    public static final String INICIO_ENVIAR = ALUMNO_CONTROLLER + "/inicio_enviar";


    // PERSONALIZAR

    private static final String CONFIGURACION_CONTROLLER = "/configuracion";
    public static final String CONFIGURACION = CONFIGURACION_CONTROLLER;
    public static final String GUARDAR_CONFIGURACION = CONFIGURACION_CONTROLLER + "/guardar";

    private PathConstants() {
    }
}
