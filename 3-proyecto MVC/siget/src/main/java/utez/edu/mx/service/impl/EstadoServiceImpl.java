package utez.edu.mx.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Estado;
import utez.edu.mx.dao.model.TipoEstado;
import utez.edu.mx.dao.repository.EstadoRepository;
import utez.edu.mx.dao.repository.TipoEstadoRepository;
import utez.edu.mx.service.EstadoService;

import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;

    @Override
    public Estado obtenerEstadoPorNombreyTipo(String nombreEstado, String nombreTipo) throws SigetException {
        TipoEstado tipoEstado = tipoEstadoRepository.findByNombre(nombreTipo);
        if(tipoEstado == null){
            throw new SigetException("Tipo estado "+nombreTipo+" no encontrado.");
        }

        Estado estado = estadoRepository.findByNombreAndTipoEstado(nombreEstado, tipoEstado);
       if(estado == null){
           throw new SigetException("Estado  "+nombreEstado+" con tipo "+nombreTipo+"no encontrado.");
       }

       return estado;

    }

    @Override
    public Estado obtenerPorId(Integer id) throws SigetException {
        Estado estado = estadoRepository.findById(id).orElse(null);
        if(estado == null){
            throw new SigetException("Estado no encontrado.");
        }
        return estado;
    }

    @Override
    public List<Estado> obtenerEstadosCita() throws SigetException {
        TipoEstado tipoEstadoCita = tipoEstadoRepository.findByNombre(GeneralConstants.TIPO_ESTADO_CITA);
        if(tipoEstadoCita == null){
            throw new SigetException("Estado no encontrado :"+GeneralConstants.TIPO_ESTADO_CITA);
        }
        return estadoRepository.findAllByTipoEstado(tipoEstadoCita);
    }
}
