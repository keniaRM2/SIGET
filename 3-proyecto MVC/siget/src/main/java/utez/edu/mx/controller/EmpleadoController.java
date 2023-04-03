package utez.edu.mx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.TemplatesConstants;
import utez.edu.mx.service.EmpleadoService;

@Controller
public class EmpleadoController extends BaseController{

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping(value = PathConstants.LISTAR_EMPLEADOS)
    public String listarEmpleados(Model model){
        model.addAttribute("empleados", empleadoService.listarEmpleados());
        return TemplatesConstants.LISTA_EMPLEADOS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_EMPLEADO)
    public String registrarEmpleado(Model model){
        model.addAttribute("empleados", empleadoService.listarEmpleados());
        return TemplatesConstants.FORMULARIO_EMPLEADO;
    }
}
