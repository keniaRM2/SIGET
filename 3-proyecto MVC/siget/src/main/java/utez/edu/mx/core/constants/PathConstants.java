package utez.edu.mx.core.constants;

import org.springframework.stereotype.Component;

@Component
public class PathConstants {

    // Paths de los controladores

    public static final String ALL_SUBPATHS = "/**";
    public static final String LOGIN = "/login";
    public static final String LOGIN_ALL_SUBPATHS = LOGIN+ALL_SUBPATHS;
    public static final String ASSETS = "/assets";
    public static final String ASSETS_ALL_SUBPATHS = ASSETS+ALL_SUBPATHS;
    public static final String INDEX = "/";
    public static final String INDEX_NAME = "/index";
    public static final String INDEX_ALL_SUBPATHS = "/**";

    public static final String ERROR = "/error";
    public static final String ERROR_ALL_SUBPATHS = ERROR+ALL_SUBPATHS;

    // Paths de las acciones del empleado

    private static final String EMPLEADO_CONTROLLER = "/empleado";
    public static final String LISTAR_EMPLEADOS = EMPLEADO_CONTROLLER;
    public static final String REGISTRAR_EMPLEADO = EMPLEADO_CONTROLLER + "/registrar";
    public static final String EDITAR_EMPLEADO = EMPLEADO_CONTROLLER + "/editar";
    public static final String ACTUALIZAR_ESTATUS_EMPLEADO = EMPLEADO_CONTROLLER + "/actualizarEstatus";
    public static final String GUARDAR_EMPLEADO = EMPLEADO_CONTROLLER + "/guardar";


    //    Paths para cita

    private static final String CITAS_CONTROLLER = "/cita";
    public static final String LISTAR_CITAS = CITAS_CONTROLLER;

    public static final String GUARDAR_CITA = CITAS_CONTROLLER + "/cita";
    public static final String REGISTRAR_CITA = CITAS_CONTROLLER + "/registrar";
    public static final String EDITAR_CITA = CITAS_CONTROLLER + "/editar";
    public static final String CANCELAR_CITA = CITAS_CONTROLLER + "/eliminar";

    private PathConstants() {
    }
}
