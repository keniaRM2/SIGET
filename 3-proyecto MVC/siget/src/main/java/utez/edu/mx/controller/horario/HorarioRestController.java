package utez.edu.mx.controller.horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.HorarioBean;
import utez.edu.mx.core.bean.ResponseRestBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.service.HorarioService;

import java.util.List;

@RestController
public class HorarioRestController extends BaseController {

    @Autowired
    private HorarioService horarioService;

    @PostMapping(value = PathConstants.VALIDAR_DISPONIBILIDAD)
    public ResponseRestBean<List<HorarioBean>> validarDisponilidad(@RequestBody CitaBean citaBean){
       try{
           return new ResponseRestBean<>(Boolean.TRUE, horarioService.validarDisponilidad(citaBean));
       }catch (SigetException e){
           return  new ResponseRestBean<>(e.getMessage());
       }
    }
}
