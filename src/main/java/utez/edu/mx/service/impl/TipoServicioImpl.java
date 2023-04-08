package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.dao.model.TipoServicio;
import utez.edu.mx.dao.repository.TipoServicioRepository;
import utez.edu.mx.service.TipoServicioService;

import java.util.List;

@Service
public class TipoServicioImpl implements TipoServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Override
    public List<TipoServicio> listarTipoServicios() {
        return tipoServicioRepository.findAll(Sort.by("id").ascending());
    }
}
