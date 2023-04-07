package utez.edu.mx.controller.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.service.TipoServicioService;


@Controller
public class TipoServicioController extends BaseController {


    @Autowired
    private TipoServicioService tipoServicioService;


    @GetMapping(value = PathConstants.LISTAR_TIPOSERVICIO)
    public String listarServicio(Model model){
        model.addAttribute(TIPOSERVICIO, tipoServicioService.listarTipoServicios());
        return VistasConstants.LISTA_TIPOSERVICIOS;
    }
}