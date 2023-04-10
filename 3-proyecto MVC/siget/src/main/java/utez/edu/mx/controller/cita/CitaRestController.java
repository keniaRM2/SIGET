package utez.edu.mx.controller.cita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.service.CitaService;
import utez.edu.mx.service.impl.CitaServiceImpl;

import java.util.List;

@RestController
public class CitaRestController extends BaseController {

    @Autowired
    private CitaService citaService;


   @GetMapping(value = PathConstants.LISTAR_CITAS)
    public List<CitaBean> listarCitas(){
       return  citaService.listarCitas();
    }

    @PostMapping(value = PathConstants.LISTAR_CITAS_RESERVACION)
    public List<CitaBean> listarCitasReservacion(@RequestBody CitaBean citaBean){
        return  citaService.listarCitasReservacion(citaBean);
    }






}
