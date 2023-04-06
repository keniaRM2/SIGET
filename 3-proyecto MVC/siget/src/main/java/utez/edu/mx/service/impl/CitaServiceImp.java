package utez.edu.mx.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.CitaRepository;
import utez.edu.mx.service.CitaService;
import utez.edu.mx.service.EstadoService;
import utez.edu.mx.service.UsuarioService;

import java.util.*;

@Service
public class CitaServiceImp implements CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private EstadoService estadoService;


    @Override
    public Cita findById(int id) {
        // Busca a una cita por su id
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean save(Cita cita) throws SigetException {
        try {
            // Guarda una cita
            return citaRepository.save(cita) != null;
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
            Cita cita=null;
            Optional<Cita> citaOptional = citaRepository.findById(id);
            if(citaOptional.isEmpty()){
                throw new SigetException("La cita no encontrada");
            }
            cita = citaOptional.get();

            Date fechaHoraActual= new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHoraActual);
            calendar.add(Calendar.HOUR, -1);

            fechaHoraActual=calendar.getTime();

            if (cita.getFechaCita().before(fechaHoraActual) &&
                    !Objects.equals(cita.getEstado().getNombre(),
                            GeneralConstants.ESTADO_CITA_RECIBIDA) &&
                    !Objects.equals(cita.getEstado().getNombre(),
                            GeneralConstants.ESTADO_CITA_NO_RECIBIDA)) {

                Estado estado = estadoService.obtenerEstadoPorNombreyTipo(GeneralConstants.ESTADO_CITA_CANCELADA,
                        GeneralConstants.TIPO_ESTADO_CITA);
                cita.setEstado(estado);
                citaRepository.save(cita);


            }else{
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

    @Override
    public List<Cita> listarCitas() {
        
        try {
            Usuario usuario = usuarioServiceImpl.obtenerUsuarioSesion();
            Rol rol = usuarioServiceImpl.obtenerRolSesion();
            if(rol.getAuthority().equals(GeneralConstants.ROL_ADMIN)){
               return citaRepository.findAll();
            } else if (rol.getAuthority().equals(GeneralConstants.ROL_EMPLEADO)) {
                Empleado empleado=usuario.getPersona().getEmpleado();
                return citaRepository.findAllByEmpleado(empleado);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }
}
