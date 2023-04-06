package utez.edu.mx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.service.EmpleadoService;

@Controller
public class EmpleadoController extends BaseController{


    @Autowired
    private EmpleadoService empleadoService;


    @GetMapping(value = PathConstants.LISTAR_EMPLEADOS)
    public String listarEmpleados(Model model){
        model.addAttribute(this.EMPLEADOS, empleadoService.listarEmpleados());
        return VistasConstants.LISTA_EMPLEADOS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_EMPLEADO)
    public String registrarEmpleado(Model model){
        model.addAttribute(this.EMPLEADO, empleadoService.obtenerEmpleadoRegistro());
        return VistasConstants.FORMULARIO_EMPLEADO;
    }


    @GetMapping(value = PathConstants.EDITAR_EMPLEADO+"/{id}")
    public String editarEmpleado(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            model.addAttribute(EMPLEADO, empleadoService.obtenerEmpleadoEdicion(id));
            return VistasConstants.FORMULARIO_EMPLEADO;
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_EMPLEADOS);
        }
    }
    @GetMapping(value = PathConstants.ACTUALIZAR_ESTATUS_EMPLEADO+"/{id}")
    public String actualizarEstatus(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            empleadoService.actualizarEstatus(id);
            mensajeExito(redirectAttributes, "Estatus actualizado correctamente.");
            return redireccionar(PathConstants.LISTAR_EMPLEADOS);
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_EMPLEADOS);
        }
    }

    @PostMapping(value = PathConstants.GUARDAR_EMPLEADO)
    public String guardarEmpleado(Empleado empleado,  Model model, RedirectAttributes redirectAttributes){
        try{
            empleadoService.guardar(empleado);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.LISTAR_EMPLEADOS);
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(EMPLEADO, empleado);
            return VistasConstants.FORMULARIO_EMPLEADO;
        }
    }
}
