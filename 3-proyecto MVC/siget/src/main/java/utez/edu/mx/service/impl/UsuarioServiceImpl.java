package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.AlumnoRepository;
import utez.edu.mx.dao.repository.RolRepository;
import utez.edu.mx.dao.repository.UsuarioRepository;
import utez.edu.mx.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl  implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;


    @Override
    public Usuario obtenerUsuarioSesion() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNombre;
        if (principal instanceof UserDetails) {
             userNombre = ((UserDetails)principal).getUsername();
        } else {
            userNombre = principal.toString();
        }
        return usuarioRepository.findByUsername(userNombre);
    }

    @Override
    public Rol obtenerRolSesion() throws SigetException {
        Usuario usuarioSesion= obtenerUsuarioSesion();
        if(usuarioSesion == null){
            throw new SigetException("Usuario no encontrado");
        }
       Optional<Rol>  rolUsuario = rolRepository.findById(usuarioSesion.getRol().getId());
        if(rolUsuario.isEmpty()){
            throw new SigetException("Rol no encontrado");
        }
        return rolUsuario.get();
    }

    @Override
    public Alumno obtenerAlumnoSesion() throws SigetException {

        Usuario usuarioSesion= obtenerUsuarioSesion();
        if(usuarioSesion == null){
            throw new SigetException("Usuario no encontrado");
        }
        Persona persona= usuarioSesion.getPersona();
        if(persona == null){
            throw new SigetException("Persona no encontrada");
        }
        Alumno alumno = persona.getAlumno();
        if(alumno == null){
            throw new SigetException("Alumno no encontrado");
        }


        Optional<Alumno>  rolAlumno = alumnoRepository.findById(alumno.getId());
        if(rolAlumno.isEmpty()){
            throw new SigetException("Alumno no encontrado verifique sus datos.");
        }
        return rolAlumno.get();
    }

    @Override
    public Boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Empleado obtenerEmpleadoSesion() throws SigetException {

        Usuario usuarioSesion= obtenerUsuarioSesion();
        if(usuarioSesion == null){
            throw new SigetException("Usuario no encontrado");
        }
        Persona persona= usuarioSesion.getPersona();
        if(persona == null){
            throw new SigetException("Persona no encontrada");
        }
        Empleado empleado = persona.getEmpleado();
        if(empleado == null){
            throw new SigetException("Alumno no encontrado");
        }
        return empleado;
    }

}
