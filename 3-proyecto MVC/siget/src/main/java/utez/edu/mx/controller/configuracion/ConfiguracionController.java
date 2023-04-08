package utez.edu.mx.controller.configuracion;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.constants.VistasConstants;
import utez.edu.mx.dao.model.Configuracion;

@Controller
@Secured("ROLE_ADMIN")
public class ConfiguracionController {
    @GetMapping(value = PathConstants.CONFIGURACION)
    public String personalizar(@ModelAttribute("configuracion") Configuracion configuracion, Model model) {
        return VistasConstants.CONFIGURACION;
    }

    @PostMapping(value = PathConstants.GUARDAR_CONFIGURACION)
    public String guardarPersonalizacion(@ModelAttribute("configuracion") Configuracion configuracion, Model model) {
        System.out.println(configuracion.toString());
        return VistasConstants.CONFIGURACION;
    }
}
