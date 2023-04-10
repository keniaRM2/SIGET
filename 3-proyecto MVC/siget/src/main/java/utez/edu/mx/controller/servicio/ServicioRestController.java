package utez.edu.mx.controller.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.ServicioBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.service.ServicioService;

@RestController
public class ServicioRestController extends BaseController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping(params = "idServicio", value = PathConstants.OBTENER_INFORMACION_SERVICIO)
    public ServicioBean obtenerInformacionServicio(@RequestParam("idServicio") Integer idServicio) {
        return servicioService.obtenerInformacionServicio(idServicio);
    }

}
