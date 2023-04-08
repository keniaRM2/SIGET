package utez.edu.mx.controller.cita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.service.impl.CitaServiceImpl;

@Controller
public class CitaController extends BaseController {

    @Autowired
    private CitaServiceImpl citasServiceImpl;


    @GetMapping(value = PathConstants.REGISTRAR_CITA)
    public String registrarCita(Model model){
        model.addAttribute(CITA, new Cita());
        return "cita/index";
    }

    @PostMapping(value = PathConstants.EDITAR_CITA)
    public String editarEstadoCita(Cita cita, Model model, RedirectAttributes redirectAttributes){
        try{
            citasServiceImpl.cambiarEstadoCita(cita);
            mensajeExito(redirectAttributes);
            return redireccionar("   ---------- falta vista---------");
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(CITA,cita);
            return "cita/index";
        }
    }

    @PostMapping(value = PathConstants.GUARDAR_CITA)
    public String guardarCita(Cita cita, Model model, RedirectAttributes redirectAttributes){
        try{
            citasServiceImpl.guardar(cita);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.LISTAR_CITAS);
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(CITA,cita);
            return "cita/index";
        }
    }




}
