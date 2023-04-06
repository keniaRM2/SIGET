package utez.edu.mx.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.repository.CitaRepository;
import utez.edu.mx.service.CitaService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CitaServiceImp implements CitaService {

    @Autowired
    private CitaRepository service;

    @Override
    public List<Cita> findAll() {
        // Busca a todas las citas
        return service.findAll();
    }

    @Override
    public List<Cita> findByEmployeeId(int id) {
        // Busca a todas las citas de un empleado
        return service.findAllById(Collections.singleton(id));
    }

    @Override
    public Cita findById(int id) {
        // Busca a una cita por su id
        return service.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean save(Cita cita) throws SigetException {
        try {
            // Guarda una cita
            return service.save(cita) != null;
        } catch (ConstraintViolationException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrores(e));
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    @Transactional
    public void cancelCita(int id) throws SigetException {
        try {
            // No puede cancelar una cita si esta esta a un día de ser cumplida
            Cita cita = service.findById(id).orElse(null);
            if (cita.getFechaCita().before(new Date())) {
                // la cita puede ser cancelada
                System.out.println("La cita puede ser cancelada");
            }else if ( cita.getFechaCita() == new Date()){
                // la cita no puede ser cancelada
                throw new SigetException("La cita no puede ser cancelada");
            }
        } catch (ConstraintViolationException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrores(e));
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }

    }
}
