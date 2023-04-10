package utez.edu.mx.controller.cita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.service.CitaService;

@Controller
public class CitaController extends BaseController {

    @Autowired
    private CitaService citaService;



    @GetMapping(value = PathConstants.REGISTRAR_CITA)
    public String registrarCita(Model model){
        model.addAttribute(CITA, new Cita());
        return "cita/index";
    }

    @PostMapping(value = PathConstants.EDITAR_ESTADO_CITA)
    public String editarEstadoCita(CitaBean cita, Model model, RedirectAttributes redirectAttributes){
        try{
            citaService.cambiarEstadoCita(cita);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.INFORMACION_CITA+"/"+cita.getId());
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.INFORMACION_CITA+"/"+cita.getId());
        }
    }

    @PostMapping(value = PathConstants.GUARDAR_CITA)
    public String guardarCita(Cita cita, Model model, RedirectAttributes redirectAttributes){
        try{
            citaService.guardar(cita);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.LISTAR_CITAS);
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(CITA,cita);
            return "cita/index";
        }
    }
    @GetMapping(value = PathConstants.INFORMACION_CITA+"/{id}")
    public String obtenerInforfmacionCita(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            model.addAttribute(CITA, citaService.obtenerInforfmacionCita(id));
            model.addAttribute(ESTADOS, citaService.obtenerEstadosCita(id));
            return VistasConstants.INFORMACION_CITA;
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.CALENDARIO_CITA_EMPLEADO);
        }
    }


}
