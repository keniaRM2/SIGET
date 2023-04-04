package utez.edu.mx.core.constants;

import org.springframework.stereotype.Component;

@Component
public class PathConstants {

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
    private static final String EMPLEADO_CONTROLLER = "/empleado";
    public static final String LISTAR_EMPLEADOS = EMPLEADO_CONTROLLER;
    public static final String REGISTRAR_EMPLEADO = EMPLEADO_CONTROLLER + "/registrar";
    public static final String EDITAR_EMPLEADO = EMPLEADO_CONTROLLER + "/editar/";
    public static final String ACTUALIZAR_ESTATUS_EMPLEADO = EMPLEADO_CONTROLLER + "/actualizarEstatus/";
    public static final String GUARDAR_EMPLEADO = EMPLEADO_CONTROLLER + "/guardar";

    private PathConstants() {
    }
}
