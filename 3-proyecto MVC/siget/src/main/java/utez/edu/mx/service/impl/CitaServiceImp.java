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

import java.util.*;

@Service
public class CitaServiceImp implements CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EmpleadoServiceImpl empleadoServiceImpl;


    @Override
    @Transactional
    public void guardar(Cita cita) throws SigetException {

        try {
            if (!Utileria.fechaAntes(cita.getFechaCita())) {
                throw new SigetException("La cita no puede registrarse, por favor seleccione una fecha válida.");
            }
            Estado estado = estadoService.obtenerEstadoPorNombreyTipo(
                    GeneralConstants.ESTADO_CITA_CANCELADA,
                    GeneralConstants.TIPO_ESTADO_CITA);

            List<Cita> citaEnpalmada = citaRepository.
                    findAllByFechaCitaAndHoraInicioBetweenOrHoraFinBetweenAndEstadoIsNotAndVentanilla(
                            cita.getFechaCita(),
                            cita.getHoraInicio(),
                            cita.getHoraFin(),
                            cita.getHoraInicio(),
                            cita.getHoraFin(),
                            estado,
                            cita.getVentanilla());
            ;
            if (Utileria.nonEmptyList(citaEnpalmada)) {
                throw new SigetException("La cita no puede registrarse, por favor ingrese otra fecha disponible.");
            }
            //// PENDIENTE ESTATUS PAGO EN PAYPAY y documentos anexos  PARA GUARDAR LA CITA
            cita.setFechaRegistro(Utileria.getFechaHoraActual());
            cita.setAlumno(usuarioServiceImpl.obtenerAlumnoSesion());
            Estado estadoPorDefecto = estadoService.
                    obtenerEstadoPorNombreyTipo(GeneralConstants.ESTADO_CITA_PROCESO,
                            GeneralConstants.TIPO_ESTADO_CITA);
            cita.setEstado(estadoPorDefecto);
            Empleado empleado = empleadoServiceImpl.obtenerEmpleadoConVentanilla(cita);
            cita.setEmpleado(empleado);
            citaRepository.save(cita);
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
    public void cambiarEstadoCita(Cita citaEstado) throws SigetException {
        Cita cita = citaRepository.findById(citaEstado.getId()).orElseThrow(() -> new SigetException("La cita no encontrada"));

        Rol rol = usuarioServiceImpl.obtenerRolSesion();
        String authority = rol.getAuthority();
        Estado estadoActual = estadoService.obtenerPorId(cita.getEstado().getId());

        if (authority.equals(GeneralConstants.ROL_ALUMNO)) {
            if (Utileria.fechaAntes(cita.getFechaCita())) {
                throw new SigetException("La cita ha caducado.");
            }
            if (!estadoActual.getNombre().equals(GeneralConstants.ESTADO_CITA_PROCESO)) {
                throw new SigetException("Cita " + estadoActual.getNombre() + ", no es posible cancelar.");
            }
            String tipo = GeneralConstants.ESTADO_CITA_CANCELADA;
            String nombre = GeneralConstants.TIPO_ESTADO_CITA;
            Estado estado = estadoService.obtenerEstadoPorNombreyTipo(nombre, tipo);
            cita.setEstado(estado);
        } else if (authority.equals(GeneralConstants.ROL_EMPLEADO)) {
            Estado nuevoEstado = estadoService.obtenerPorId(citaEstado.getEstado().getId());
            String nombreNuevoEstado = nuevoEstado.getNombre();
            String estadoCita = estadoActual.getNombre();
            String aceptada = GeneralConstants.ESTADO_CITA_ACEPTADA;
            String proceso = GeneralConstants.ESTADO_CITA_PROCESO;
            String cancelada = GeneralConstants.ESTADO_CITA_CANCELADA;
            String rebibida = GeneralConstants.ESTADO_CITA_RECIBIDA;
            String noRecibida = GeneralConstants.ESTADO_CITA_NO_RECIBIDA;
            if (Utileria.fechaAntes(cita.getFechaCita())) {
                throw new SigetException("La cita ha caducado.");
            }
            if ((nombreNuevoEstado.equals(aceptada) || nombreNuevoEstado.equals(cancelada)) && !estadoCita.equals(proceso)) {
                throw new SigetException("Cita " + estadoCita + ", no es posible cambiar el estado.");
            } else if ((nombreNuevoEstado.equals(rebibida) || nombreNuevoEstado.equals(noRecibida)) && !estadoCita.equals(aceptada)) {
                throw new SigetException("Cita " + estadoCita + ", no es posible cambiar el estado.");
            }
            // CHECAR EL ENVIO DE EMAIL PENDIENTE
            cita.setEstado(nuevoEstado);
        } else {
            throw new SigetException("Permisos denegados");
        }

        citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {

        try {
            Usuario usuario = usuarioServiceImpl.obtenerUsuarioSesion();
            Rol rol = usuarioServiceImpl.obtenerRolSesion();
            if (rol.getAuthority().equals(GeneralConstants.ROL_ADMIN)) {
                return citaRepository.findAll();
            } else if (rol.getAuthority().equals(GeneralConstants.ROL_EMPLEADO)) {
                Empleado empleado = usuario.getPersona().getEmpleado();
                return citaRepository.findAllByEmpleado(empleado);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ArrayList<>();

    }
}
