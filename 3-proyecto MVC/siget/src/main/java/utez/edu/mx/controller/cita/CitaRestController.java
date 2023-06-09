package utez.edu.mx.controller.cita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.CitaRegistroBean;
import utez.edu.mx.core.bean.ResponseRestBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.service.CitaService;

import java.util.List;

@RestController
public class CitaRestController extends BaseController {

    @Autowired
    private CitaService citaService;


    @GetMapping(value = PathConstants.LISTAR_CITAS)
    public List<CitaBean> listarCitas() {
        return citaService.listarCitas();
    }

    @PostMapping(value = PathConstants.LISTAR_CITAS_RESERVACION)
    public List<CitaBean> listarCitasReservacion(@RequestBody CitaBean citaBean) {
        return citaService.listarCitasReservacion(citaBean);
    }
    @PostMapping(value = PathConstants.OBTENER_INFORMACION_CITA)
    public CitaBean obtenerInformacionCita(@RequestBody CitaBean citaBean) {
        return citaService.obtenerInforfmacionCita(citaBean.getId());
    }


    @PostMapping(params = "idServicio", value = PathConstants.AUTORIZAR_PAGO)
    public ResponseRestBean<String> autorizarPago(@RequestParam("idServicio") Integer idServicio) {
        try {
            return new ResponseRestBean<>(Boolean.TRUE, citaService.autorizarPago(idServicio));
        } catch (SigetException e) {
            return new ResponseRestBean<>(e.getMessage());
        }
    }


}
