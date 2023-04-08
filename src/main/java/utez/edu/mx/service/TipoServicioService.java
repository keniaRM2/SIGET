package utez.edu.mx.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.TipoServicio;

import java.util.List;

@Service
public interface TipoServicioService {

    List<TipoServicio> listarTipoServicios();


}

