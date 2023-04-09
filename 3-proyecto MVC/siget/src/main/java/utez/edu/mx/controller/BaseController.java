package utez.edu.mx.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BaseController {


    public static final String AJAX_HEADER_NAME = "X-Requested-With";
    public static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
    public static final String ALUMNOS = "alumnos";
    public static final String ALUMNO = "alumno";

    public static final String CITAS = "citas";
    public static final String CITA = "cita";

    public static final String EMPLEADO = "empleado";
    public static final String EMPLEADOS = "empleados";
    public static final String ESTADOS = "estados";


    public static final String SERVICIO = "servicio";
    public static final String TIPOSERVICIO = "tiposervicio";
    public static final String SERVICIOS= "servicios";
    public static final String TIPOSERVICIOS= "tiposervicios";


    private static final String REDIRECT = "redirect:";







    protected static final String MENSAJE_EXITO_KEY = "mensajeExito";
    private static final String MENSAJE_EXITO_VALUE = "Se ha realizado la acción correctamente.";
    protected static final String MENSAJE_ERROR_KEY = "mensajeError";
    private static final String MENSAJE_ERROR_VALUE = "Ocurrió un error interno.";

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(formateador, false));
    }

    public String redireccionar(String vista) {
        return REDIRECT.concat(vista);
    }

    public void mensajeExito(RedirectAttributes redirectAttributes) {
        agregarFlashAttribute(redirectAttributes, MENSAJE_EXITO_KEY, MENSAJE_EXITO_VALUE);
    }
    public void mensajeExito(RedirectAttributes redirectAttributes, String mensaje) {
        agregarFlashAttribute(redirectAttributes, MENSAJE_EXITO_KEY, mensaje);
    }

    public void mensajeError(RedirectAttributes redirectAttributes) {
        agregarFlashAttribute(redirectAttributes, MENSAJE_ERROR_KEY, MENSAJE_ERROR_VALUE);

    }
    public void mensajeError(RedirectAttributes redirectAttributes, String mensaje) {
        agregarFlashAttribute(redirectAttributes, MENSAJE_ERROR_KEY, mensaje);
    }
    public void mensajeError(Model model, String mensaje) {
        agregarModelAttribute(model, MENSAJE_ERROR_KEY, mensaje);
    }
    public void mensajeError(Model model) {
        agregarModelAttribute(model, MENSAJE_ERROR_KEY, MENSAJE_ERROR_VALUE);
    }
    public void agregarModelAttribute(Model model, String key, Object value){
        model.addAttribute(key, value);
    }

    public void agregarFlashAttribute(RedirectAttributes redirectAttributes, String key, Object value){
        redirectAttributes.addFlashAttribute(key, value);
    }

}
