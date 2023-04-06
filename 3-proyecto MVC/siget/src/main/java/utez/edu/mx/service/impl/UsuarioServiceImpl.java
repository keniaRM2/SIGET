package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Rol;
import utez.edu.mx.dao.model.Usuario;
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
}
