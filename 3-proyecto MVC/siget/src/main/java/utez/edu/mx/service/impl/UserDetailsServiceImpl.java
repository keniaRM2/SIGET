package utez.edu.mx.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import utez.edu.mx.dao.model.Rol;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.RolRepository;
import utez.edu.mx.dao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(usernameOrEmail);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario "+usernameOrEmail+" no encontrado.");
        }
        String authority = rolRepository.findById(usuario.getRol().getId()).get().getAuthority();
        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(authority));
        return new  org.springframework.security.core.userdetails.User(usuario.getUsername(),usuario.getPassword(), usuario.getEnabled() == 1, true, true, true, authorityList);

    }

}
