package utez.edu.mx.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.ServicioRepository;
import utez.edu.mx.dao.repository.TipoServicioRepository;
import utez.edu.mx.service.ServicioService;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Override
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void guardar(Servicio servicio) throws SigetException {
        if (Utileria.nonNull(servicio.getId())) {
            actualizar(servicio);
        } else {
            registrar(servicio);
        }
    }


    @Override
    @Transactional
    public void registrar(Servicio servicio) throws SigetException {
        try {
            Servicio servicioRepetido = servicioRepository.findByNombre(servicio.getNombre());
            if (Utileria.nonNull(servicioRepetido)) {
                throw new SigetException("El servicio se ha registrado previamente");
            }
            TipoServicio tipoServicio = servicio.getTipoServicio();
            servicio.setTipoServicio(tipoServicio);
            servicioRepository.save(servicio);

        } catch (ConstraintViolationException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrores(e));
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }
//ACTUALIZA
@Override
@Transactional
public void actualizar(Servicio servicio) throws SigetException {
    try {

        Optional<Servicio> servicioOptional = servicioRepository.findById(servicio.getId());

        if (servicioOptional.isEmpty()) {
            throw new SigetException("servicio no encontrado.");
        }
/*
            servicio servicioRepetido = servicioRepository.findByNombre(servicio.getNombre());
            if (Utileria.nonNull(servicioRepetido)) {
                throw new SigetException("El servicio se ha registrado previamente");
            }

*/
        TipoServicio tipoServicio = servicio.getTipoServicio();
        servicio.setTipoServicio(tipoServicio);
        servicioRepository.save(servicio);

    } catch (ConstraintViolationException e) {
        System.err.println(e.getMessage());
        throw new SigetException(Utileria.getErrores(e));
    } catch (NullPointerException e) {
        System.err.println(e.getMessage());
        throw new SigetException(Utileria.getErrorNull());
    }
}

    @Override
    public Servicio obtenerServicioRegistro() {
        Servicio servicio = new Servicio();
        TipoServicio tipoServicio = new TipoServicio();
        servicio.setTipoServicio(tipoServicio);
        return servicio;
    }

    @Override
    public Servicio obtenerServicioEdicion(Integer id) throws SigetException {

        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if (servicioOptional.isEmpty()) {
            throw new SigetException("servicio no encontrado.");
        }
        return servicioOptional.get();
    }

    @Override
    @Transactional
    public void actualizarEstatus(Integer id) throws SigetException {

        try {
            Optional<Servicio> servicioOptional = servicioRepository.findById(id);
            if (servicioOptional.isEmpty()) {
                throw new SigetException("servicio no encontrado.");
            }

            Servicio servicio = servicioOptional.get();
            if (servicio.getEstatus() == 1) {
                servicio.setEstatus(0);
            }else{
                servicio.setEstatus(1);
            }
            servicioRepository.save(servicio);

        } catch (ConstraintViolationException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrores(e));
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }

    }
}
