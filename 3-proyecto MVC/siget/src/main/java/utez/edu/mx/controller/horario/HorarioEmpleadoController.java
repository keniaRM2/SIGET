package utez.edu.mx.controller.horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.service.HorarioEmpleadoService;

@Controller
public class HorarioEmpleadoController extends BaseController {

    @Autowired
    private HorarioEmpleadoService horarioEmpleadoService;


    @GetMapping(value = PathConstants.LISTAR_HORARIOS_EMPLEADO)
    public String listarHorariosEmpleados(Model model){
        model.addAttribute(HORARIOS, horarioEmpleadoService.listarHorario());
        model.addAttribute("dias", horarioEmpleadoService.listarDias());
        model.addAttribute("horasInicio", horarioEmpleadoService.listarHoras());
        model.addAttribute("horasFin", horarioEmpleadoService.listarHoras());
        return VistasConstants.LISTA_HORARIOS_EMPLEADO;
    }



}
