package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.RolRepository;
import utez.edu.mx.dao.repository.UsuarioRepository;

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

        boolean activo = usuario.getEnabled() == 1;

        return new User(usuario.getUsername(),  usuario.getPassword(), activo, true, true, true, authorityList);

    }

}
