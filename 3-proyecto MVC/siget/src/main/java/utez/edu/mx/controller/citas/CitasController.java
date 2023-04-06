package utez.edu.mx.controller.citas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.UsuarioRepository;
import utez.edu.mx.service.impl.CitaServiceImp;

@Controller
public class CitasController {

    @Autowired
    private CitaServiceImp service;

    // Se obtiene el usuario que inicio sesión

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping(value = PathConstants.LISTAR_CITAS)
    @Secured("ROLE_ADMIN")
    public String indexAdmin (@ModelAttribute("cita")Cita cita, Model model){
    //        Solo el administrador puede ver todas las citas
        String[] array = service.findAll().toArray(new String[100]);
        model.addAttribute("citas", array);
        return "citas/index";
    }


    @GetMapping(value = PathConstants.LISTAR_CITAS)
    @Secured("ROLE_EMPLEADO")
    public String indexEmpleado (@ModelAttribute("cita")Cita cita, Model model, Authentication authentication){
        //       Solo el empleado puede ver sus citas
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName());
        String [] array = service.findByEmployeeId(Math.toIntExact(usuario.getId())).toArray(new String[100]);
        model.addAttribute("citas", array);
        return "citas/index";
    }



    @GetMapping(value = PathConstants.LISTAR_CITAS_SEMANA)
    @Secured("ROLE_ADMIN")
    public String indexAdminSemana (@ModelAttribute("cita")Cita cita, Model model){
        //       Citas de la semana vistas por el administrador
        model.addAttribute("citas", service.findAll());
        return "citas/index";
    }


}
