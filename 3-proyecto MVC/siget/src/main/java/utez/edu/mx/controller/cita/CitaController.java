package utez.edu.mx.controller.cita;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.ArchivoBean;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.CitaRegistroBean;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.service.CitaService;
import utez.edu.mx.service.ServicioService;
import utez.edu.mx.service.UsuarioService;

import java.util.Date;

@Controller
public class CitaController extends BaseController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = PathConstants.GUARDAR_CITA)
    public ResponseEntity<String> guardarCita(CitaRegistroBean cita) {
        try {
             Integer idCita = citaService.guardar(cita);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("idCita", idCita.toString())
                    .body(null);
        } catch (SigetException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(MENSAJE_ERROR_KEY, e.getMessage())
                    .body( e.getMessage());

        }
    }

    @GetMapping(value = PathConstants.REGISTRAR_CITA)
    @Secured({"ROLE_ALUMNO"})
    public String registrarCita(Model model) {
        model.addAttribute(CITA, citaService.obtenerCitaRegistro());
        model.addAttribute(SERVICIOS, servicioService.listarServiciosActivos());
        return VistasConstants.FORMULARIO_CITA;
    }

    @GetMapping(value = PathConstants.LISTAR_MIS_CITAS)
    @Secured({"ROLE_ALUMNO"})
    public String listarMisCitas(Model model) {
        model.addAttribute(CITAS, citaService.listarMisCitas());
        return VistasConstants.HISTORIAL_CITA;
    }

    @PostMapping(value = PathConstants.EDITAR_ESTADO_CITA)
    public String editarEstadoCita(CitaBean cita, Model model, RedirectAttributes redirectAttributes) {
        try {
            citaService.cambiarEstadoCita(cita);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.INFORMACION_CITA + "/" + cita.getId());
        } catch (SigetException e) {
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.INFORMACION_CITA + "/" + cita.getId());
        }
    }


    @GetMapping(value = PathConstants.INFORMACION_CITA + "/{id}")
    @Secured({"ROLE_ALUMNO"})
    public String obtenerInforfmacionCita(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            model.addAttribute(CITA, citaService.obtenerInforfmacionCita(id));
            model.addAttribute(ESTADOS, citaService.obtenerEstadosCita(id));
            return VistasConstants.INFORMACION_CITA;
        } catch (SigetException e) {
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.INDEX);
        }
    }


    @GetMapping(value = PathConstants.REALIZAR_PAGO)
    @Secured({"ROLE_ALUMNO"})
    public String realizarPago(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        try {

            String idPago = request.getParameter(GeneralConstants.ID_PAGO_PAYPAL);
            String idEmisor = request.getParameter(GeneralConstants.ID_EMISIOR_PAYPAL);

            citaService.realizarPago(idPago, idEmisor);

            return redireccionar(PathConstants.COMPROBANTE_PAGO);

        } catch (Exception e) {
            mensajeError(model, e.getMessage());
            return VistasConstants.ERROR;
        }
    }

    @GetMapping(value = PathConstants.COMPROBANTE_PAGO)
    public String visualizarComprobantePago(Model model) {
        model.addAttribute("fechaRegistro", new Date());
        model.addAttribute("emisor", usuarioService.obtenerUsuarioSesion().getPersona().getNombreCompleto());
        return VistasConstants.COMPROBANTE_PAGO;
    }

    @GetMapping(value = PathConstants.DESCARGAR_DOCUMENTO+"/{id}")
    public ResponseEntity<byte[]> descargarDocumento( @PathVariable Integer id) {
        ArchivoBean archivo = citaService.obtenerArchivoAnexo(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
                .body(archivo.getArchivo());
    }

    @GetMapping(value = PathConstants.DESCARGAR_COMPROBANTE_PAGO+"/{id}")
    public ResponseEntity<byte[]> descargarComprobantePago( @PathVariable Integer id) {
        ArchivoBean archivo = citaService.obtenerComprobantePago(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
                .body(archivo.getArchivo());
    }


}
