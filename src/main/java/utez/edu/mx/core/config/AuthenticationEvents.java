package utez.edu.mx.core.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.service.BitacoraAccesoService;

@Component
public class AuthenticationEvents implements LogoutHandler {

    @Autowired
    private BitacoraAccesoService bitacoraAccesoService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) throws SigetException {
        User usuario = (User) success.getAuthentication().getPrincipal();
        String username = usuario.getUsername();
        bitacoraAccesoService.registrarIngreso(username);
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User usuario = (User) authentication.getPrincipal();
        String username = usuario.getUsername();
        bitacoraAccesoService.registrarSalida(username);
    }
}
