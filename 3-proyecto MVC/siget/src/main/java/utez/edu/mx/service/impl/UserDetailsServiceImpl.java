package utez.edu.mx.service.impl;

import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

//        Usuario user = userRepository.findByEmail(usernameOrEmail);
//        if (user != null) {
//            return new org.springframework.security.core.userdetails.Usuario(user.getEmail()
//                    , user.getPassword(),
//                    user.getRoles().stream()
//                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                            .collect(Collectors.toList()));
//        } else {
//            throw new UsernameNotFoundException("Invalid email or password");
//        }
        Usuario usuario = usuarioRepository.findByUsername(usernameOrEmail);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario "+usernameOrEmail+" no encontrado.");
        }

        return new  org.springframework.security.core.userdetails.User(usuario.getUsername(),
                usuario.getPassword(), Collections.emptyList());

    }

}
