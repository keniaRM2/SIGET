package utez.edu.mx.controller.empleado;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.service.EmpleadoService;

@Controller
public class EmpleadoController extends BaseController {


    @Autowired
    private EmpleadoService empleadoService;


    @GetMapping(value = PathConstants.LISTAR_EMPLEADOS)
    public String listarEmpleados(Model model){
        model.addAttribute(EMPLEADOS, empleadoService.listarEmpleados());
        return VistasConstants.LISTA_EMPLEADOS;
    }

    @GetMapping(value = PathConstants.INICIO_EMPLEADO)
    public String inicioEmpleado(Model model){
        return VistasConstants.INICIO_EMPLEADOS;
    }
    @GetMapping(value = PathConstants.BANDEJA_CITAS)
    public String bandejaCitas (Model model){
        return VistasConstants.BANDEJA_CITAS;
    }
    @GetMapping(value = PathConstants.HORARIO_CITAS)
    public String horaioEmpleado(Model model){
        return VistasConstants.HORARIO_CITAS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_EMPLEADO)
    public String registrarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model){
        model.addAttribute(EMPLEADO, empleadoService.obtenerEmpleadoRegistro());
        return VistasConstants.FORMULARIO_EMPLEADO;
    }


    @GetMapping(value = PathConstants.EDITAR_EMPLEADO+"/{id}")
    public String editarEmpleado(@ModelAttribute("empleado") Empleado empleado, @PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            model.addAttribute(EMPLEADO, empleadoService.obtenerEmpleadoEdicion(id));
            return VistasConstants.FORMULARIO_EMPLEADO;
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_EMPLEADOS);
        }
    }
    @GetMapping(value = PathConstants.ACTUALIZAR_ESTATUS_EMPLEADO+"/{id}")
    public String actualizarEstatus(@ModelAttribute("empleado") Empleado empleado, @PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
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
    public String guardarEmpleado(@Valid @ModelAttribute("empleado")Empleado empleado, BindingResult result,Errors errors,  Model model, RedirectAttributes redirectAttributes){
        try{
          String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    System.out.println(error.getDefaultMessage());
                }

                return VistasConstants.FORMULARIO_EMPLEADO;
            }

            if (!empleado.getPersona().getUsuario().getUsername().matches(regex)) {
                model.addAttribute("errorCorreo", "El correo tiene un formato incorrecto");
                return VistasConstants.FORMULARIO_EMPLEADO;
            }

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