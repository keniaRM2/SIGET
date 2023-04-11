package utez.edu.mx.controller.alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Alumno;
import utez.edu.mx.dao.model.DivisionAcademica;
import utez.edu.mx.service.AlumnoService;
import utez.edu.mx.service.CarreraService;
import utez.edu.mx.service.DivisionAcademicaService;

@Controller
public class AlumnoController extends BaseController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private DivisionAcademicaService divisionAcademicaService;

    @Autowired
    private CarreraService carreraService;

    @GetMapping(value = PathConstants.LISTAR_ALUMNOS)
    public String listarAlumnos(Model model){
        model.addAttribute(ALUMNOS, alumnoService.listarAlumnos());
        return VistasConstants.LISTA_ALUMNOS;
    }

    @GetMapping(value = PathConstants.REGISTRAR_ALUMNO)
    public String registrarAlumno(Model model){
        model.addAttribute(ALUMNO, alumnoService.obtenerAlumnoRegistro());
        model.addAttribute(CARRERAS, carreraService.listarCarreras());
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
            return redireccionar(PathConstants.LOGIN);
        }catch (SigetException e){
            mensajeError(model, e.getMessage());
            model.addAttribute(ALUMNO, alumno);
            model.addAttribute(CARRERAS, carreraService.listarCarreras());
            return VistasConstants.FORMULARIO_ALUMNO;
        }
    }
    @GetMapping(value = PathConstants.INICIO_ALUMNO)
    public String inicioAlumno(Model model){
        return VistasConstants.INICIO_ALUMNO;
    }

    @GetMapping(value = PathConstants.HISTORIAL_ALUMNO)
    public String historialAlumno(Model model){
        return VistasConstants.HISTORIAL_ALUMNO;
    }

    @GetMapping(value = PathConstants.INICIO_FECHA)
    public String inicioFecha(Model model){
        return VistasConstants.INICIO_FECHA;
    }

    @GetMapping(value = PathConstants.INICIO_PAGO)
    public String inicioPago(Model model){
        return VistasConstants.INICIO_PAGO;
    }

    @GetMapping(value = PathConstants.INICIO_ENVIAR)
    public String inicioEnviar(Model model) {
        return VistasConstants.INICIO_ENVIAR;
    }
}