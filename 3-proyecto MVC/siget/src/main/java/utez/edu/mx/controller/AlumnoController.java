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
import utez.edu.mx.dao.model.Alumno;
import utez.edu.mx.service.AlumnoService;

@Controller
public class AlumnoController extends BaseAlumnoController{


    @Autowired
    private AlumnoService alumnoService;


    @GetMapping(value = PathConstants.LISTAR_ALUMNOS)
    public String listarAlumnos(Model model){
        model.addAttribute(ALUMNOS, alumnoService.listarAlumnos());
        return VistasConstants.LISTA_ALUMNOS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_ALUMNO)
    public String registrarAlumno(Model model){
        model.addAttribute(ALUMNO, alumnoService.obtenerAlumnoRegistro());
        return VistasConstants.FORMULARIO_ALUMNO;
    }


    @GetMapping(value = PathConstants.EDITAR_ALUMNO+"/{id}")
    public String editarAlumno(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            model.addAttribute(ALUMNO, alumnoService.obtenerAlumnoEdicion(id));
            return VistasConstants.FORMULARIO_ALUMNO;
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_ALUMNOS);
        }
    }
    @GetMapping(value = PathConstants.ACTUALIZAR_ESTATUS_ALUMNO+"/{id}")
    public String actualizarEstatus(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes){
        try{
            alumnoService.actualizarEstatus(id);
            mensajeExito(redirectAttributes, "Estatus actualizado correctamente.");
            return redireccionar(PathConstants.LISTAR_ALUMNOS);
        }catch (SigetException e){
            mensajeError(redirectAttributes, e.getMessage());
            return redireccionar(PathConstants.LISTAR_ALUMNOS);
        }
    }

    @PostMapping(value = PathConstants.GUARDAR_ALUMNO)
    public String guardarAlumno(Alumno alumno,  Model model, RedirectAttributes redirectAttributes){
        try{
            alumnoService.guardar(alumno);
            mensajeExito(redirectAttributes);
            return redireccionar(PathConstants.LOGIN); //redireccionar a servicios
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(ALUMNO, alumno);
            return VistasConstants.LOGIN;
        }
    }
}