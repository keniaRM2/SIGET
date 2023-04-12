package utez.edu.mx.controller.recuperar_contrasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.dao.model.EmailDetails;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.service.impl.EmailServiceImpl;
import utez.edu.mx.service.impl.UsuarioServiceImpl;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RecuperarContrasena {

    //Se inyecta el servicio de usuario para poder utilizar sus metodos
    @Autowired
    private UsuarioServiceImpl usuarioService;

    //Se utiliza la inyeccion de dependencias para poder utilizar el envio de correos
    @Autowired
    private EmailServiceImpl emailService;

    Usuario usuarioActual = new Usuario();

    String codigoGenerado = null;

    //    Redirige a la vista de recuperar contraseña
    @GetMapping(value = PathConstants.RECUPERAR_CONTRASENA)
    public String recuperarContrasena() {
        return VistasConstants.RECUPERAR_CONTRASENA;
    }

    //    Redirige a la vista de codigo de recuperacion, enviando el codigo generado y obtiene el usuario actual
    @PostMapping(value = PathConstants.ENVIAR_CODIGO_RECUPERAR_CONTRASENA)
    public String enviarCodigoRecuperacion(@RequestParam String username) {
        if (!usuarioService.existeUsuario(username)) {
            return "redirect:"+PathConstants.RECUPERAR_CONTRASENA+"?error";
        }
        usuarioActual = usuarioService.obtenerUsuarioPorUsername(username);
        codigoGenerado = generateRandomPassword();
        emailService.sendSimpleMail(new EmailDetails(username, "Tú código de recuperación es: " + codigoGenerado, "Código de recuperación"));
        return "redirect:"+ PathConstants.CODIGO_RECUPERAR_CONTRASENA;
    }

    //   Redirige a la vista de codigo de recuperacion
    @GetMapping(value = PathConstants.CODIGO_RECUPERAR_CONTRASENA)
    public String codigoRecuperarContrasena() {
        if (!usuarioService.existeUsuario(usuarioActual.getUsername())){
            return "redirect:"+PathConstants.LOGIN+"?denied";
        }
        return VistasConstants.ENVIAR_CORREO;
    }

    // Valida el codigo de recuperacion y redirige a la vista de nueva contraseña
    @PostMapping(value = PathConstants.VALIDAR_CODIGO_RECUPERAR_CONTRASENA)
    public String validarCodigos(@RequestParam String codigo1,
                                 @RequestParam String codigo2,
                                 @RequestParam String codigo3,
                                 @RequestParam String codigo4,
                                 @RequestParam String codigo5,
                                 @RequestParam String codigo6) {

        if (!usuarioService.existeUsuario(usuarioActual.getUsername())){
            return "redirect:"+PathConstants.LOGIN+"?denied";
        }
        String codigo = codigo1 + codigo2 + codigo3 + codigo4 + codigo5 + codigo6;
        if (!codigo.equals(codigoGenerado)) {
            return "redirect:"+PathConstants.CODIGO_RECUPERAR_CONTRASENA+"?error";
        }
        return "redirect:"+PathConstants.NUEVA_CONTRASENA;
    }

    // Redirige a la vista de nueva contraseña
    @GetMapping(value = PathConstants.NUEVA_CONTRASENA)
    public String nuevaContrasena() {
        if (!usuarioService.existeUsuario(usuarioActual.getUsername())) {
            return "redirect:"+PathConstants.LOGIN+"?denied";
        }
        System.out.println("Usuario actual: " + usuarioActual.getUsername());
        return VistasConstants.NUEVA_CONTRASENA;
    }


    // Valida contraseñas y guarda la nueva contraseña
    @PostMapping(value = PathConstants.ENVIAR_CAMBIOS)
    public String enviarCorreo(@RequestParam String password, @RequestParam String repetirPassword) {
        //Se valida que el usuario exista
        if (!usuarioService.existeUsuario(usuarioActual.getUsername())) {
            return "redirect:"+PathConstants.LOGIN+"?denied";
        }
        //Validar que las contraseñas sean iguales
        if (!password.equals(repetirPassword)) {
            return "redirect:"+PathConstants.NUEVA_CONTRASENA+"?error";
        }
        // Se genera una contraseña aleatoria para posteriormente encriptarla
        int strenght = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strenght, new SecureRandom());

        String newPassword = bCryptPasswordEncoder.encode(password);
        //Se obtiene al usuario y se actualiza la contraseña del usuario

        usuarioActual.setPassword(newPassword);
        usuarioService.actualizarUsuario(usuarioActual);
        usuarioActual = new Usuario();
        codigoGenerado = null;

        return "redirect:"+PathConstants.LOGIN+"?sent";
    }


    // Método para generar un código alfanumérica aleatoria de una longitud específica (6)
    public static String generateRandomPassword() {
        // Rango ASCII – alfanumérico (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        // cada iteración del bucle elige aleatoriamente un carácter del dado

        return IntStream.range(0, 6)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }
}
