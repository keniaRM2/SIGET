package utez.edu.mx.controller.servicio;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Servicio;
import utez.edu.mx.service.ServicioService;
import utez.edu.mx.service.impl.TipoServicioImpl;

@Controller
public class ServicioController extends BaseController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private TipoServicioImpl tipoServicio;


    @GetMapping(value = PathConstants.LISTAR_SERVICIOS)
    public String listarServicios(Model model) {
        model.addAttribute(SERVICIOS, servicioService.listarServicios());
        model.addAttribute("tipoServicio", tipoServicio.listarTipoServicios());
        return VistasConstants.LISTA_SERVICIOS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_SERVICIO)
    @Secured({"ROLE_ADMIN"})
    public String registrarServicio(Servicio servicio, Model model, HttpServletRequest request) {

        servicio = servicioService.obtenerServicioRegistro(servicio);
        model.addAttribute(SERVICIO, servicio);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return VistasConstants.FORMULARIO_SERVICIO + "::#items";
        }

        return VistasConstants.FORMULARIO_SERVICIO;
    }

    @PostMapping(params = "indice", value = PathConstants.REMOVER_REMOVER_DOCUMENTO)
    public String removerDocumento(Servicio servicio, @RequestParam("indice") int indice, Model model, HttpServletRequest request) {
        servicio = servicioService.removerDocumento(servicio, indice);
        model.addAttribute(SERVICIO, servicio);
        return VistasConstants.FORMULARIO_SERVICIO + "::#items";
    }

    @GetMapping(value = PathConstants.EDITAR_SERVICIO + "/{id}")
    @Secured({"ROLE_ADMIN"})
    public String editarServicio(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("tipoServicio", tipoServicio.listarTipoServicios());
            model.addAttribute(SERVICIO, servicioService.obtenerServicioEdicion(id));

            return VistasConstants.FORMULARIO_SERVICIO;
        } catch (SigetException e) {
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_SERVICIOS);
        }
    }

    @GetMapping(value = PathConstants.ACTUALIZAR_ESTATUS_SERVICIO + "/{id}")
    @Secured({"ROLE_ADMIN"})
    public String actualizarServicio(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            servicioService.actualizarEstatus(id);
            mensajeExito(redirectAttributes, "Estatus actualizado correctamente.");
            return redireccionar(PathConstants.LISTAR_SERVICIOS);
        } catch (SigetException e) {
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_SERVICIOS);
        }
    }

    @PostMapping(value = PathConstants.GUARDAR_SERVICIO)
    public String guardarServicio(Servicio servicio, Model model, RedirectAttributes redirectAttributes) {
        try {
            servicioService.guardar(servicio);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.LISTAR_SERVICIOS);
        } catch (SigetException e) {
            mensajeError(model, e.getMessage());
            model.addAttribute(SERVICIO, servicio);
            return VistasConstants.FORMULARIO_SERVICIO;
        }
    }
}
