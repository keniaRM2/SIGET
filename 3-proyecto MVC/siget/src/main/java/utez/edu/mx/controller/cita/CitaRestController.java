package utez.edu.mx.controller.cita;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.service.impl.CitaServiceImpl;

import java.util.List;

@RestController
public class CitaRestController extends BaseController {

    @Autowired
    private CitaServiceImpl citasServiceImpl;


   @GetMapping(value = PathConstants.LISTAR_CITAS)
    public List<CitaBean> listarCitas(){
       return  citasServiceImpl.listarCitas();
    }






}
