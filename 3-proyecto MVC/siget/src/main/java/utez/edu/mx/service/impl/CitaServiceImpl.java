package utez.edu.mx.service.impl;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.core.bean.*;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.CitaRepository;
import utez.edu.mx.dao.repository.DocumentoAnexoRepository;
import utez.edu.mx.service.CitaService;
import utez.edu.mx.service.EstadoService;
import utez.edu.mx.service.PayPalService;
import utez.edu.mx.service.ServicioService;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private DocumentoAnexoRepository documentoAnexoRepository;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EmpleadoServiceImpl empleadoServiceImpl;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PayPalService payPalService;

    @Override
    public Cita obtenerCita(Integer id) throws SigetException {
        return citaRepository.findById(id).orElseThrow(() -> new SigetException("Cita no encontrada"));
    }

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
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    @Transactional
    public void cambiarEstadoCita(CitaBean citaEstado) throws SigetException {
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
            String tipo = GeneralConstants.TIPO_ESTADO_CITA;
            String nombre = GeneralConstants.ESTADO_CITA_CANCELADA;
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
            if (Utileria.fechaAntes(cita.getFechaCita()) && !(nombreNuevoEstado.equals(noRecibida))) {

                throw new SigetException("La cita ha caducado.");

            }
            if ((nombreNuevoEstado.equals(aceptada) || nombreNuevoEstado.equals(cancelada)) && !estadoCita.equals(proceso)) {
                throw new SigetException("Cita " + estadoCita + ", no es posible cambiar el estado.");
            } else if ((nombreNuevoEstado.equals(rebibida)) && !estadoCita.equals(aceptada)) {
                throw new SigetException("Cita " + estadoCita + ", no es posible cambiar el estado.");
            }

            // Si la cita fue aceptada o cancelada enviara un correo al alumno informando de la cituación
            String recipient = cita.getAlumno().getPersona().getUsuario().getUsername();
            if ( nombreNuevoEstado.equals(aceptada) ) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cita.getFechaCita());
                // obtener día, mes y año sin horas
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);

                String fecha = dia + "/" + mes + "/" + anio;
                String msgBody = "Buen día "+cita.getAlumno().getPersona().getNombre()+" "+cita.getAlumno().getPersona().getPrimerApellido()+" "+cita.getAlumno().getPersona().getSegundoApellido()+", su cita ha sido aceptada para el día , "+fecha+" a la hora : "+ cita.getHoraInicio() +" puede consultar su estado directamente en la pagina SIGET, deberá llegar con 15 minutos de anticipación.";
                emailService.sendSimpleMail(new EmailDetails( recipient, msgBody, "Cita aceptada"));
            } else if ( nombreNuevoEstado.equals(cancelada) ) {
                String msgBody = "Buen día "+cita.getAlumno().getPersona().getNombre()+" "+cita.getAlumno().getPersona().getPrimerApellido()+" "+cita.getAlumno().getPersona().getSegundoApellido()+", su cita ha sido cancelada, para más detalles puede consultar su estado directamente en ventanilla de manera presencial o en la plataforma SIGET.";
                emailService.sendSimpleMail(new EmailDetails( recipient, msgBody , "Cita cancelada"));
            }

            cita.setEstado(nuevoEstado);
        } else {
            throw new SigetException("Permisos denegados");
        }

        citaRepository.save(cita);
    }

    @Override
    @Transactional
    public List<CitaBean> listarCitas() {
        List<CitaBean> citasBean = new ArrayList<>();

        try {
            List<Cita> citas = null;
            Usuario usuario = usuarioServiceImpl.obtenerUsuarioSesion();
            Rol rol = usuarioServiceImpl.obtenerRolSesion();

            if (rol.getAuthority().equals(GeneralConstants.ROL_ADMIN)) {
                citas = citaRepository.findAll();
            } else if (rol.getAuthority().equals(GeneralConstants.ROL_EMPLEADO)) {
                Empleado empleado = usuario.getPersona().getEmpleado();
                citas = citaRepository.findAllByEmpleado(empleado);
            }

            String tipo = GeneralConstants.TIPO_ESTADO_CITA;
            String nombreNoRecibida = GeneralConstants.ESTADO_CITA_NO_RECIBIDA;
            String nombreCancelado = GeneralConstants.ESTADO_CITA_NO_RECIBIDA;
            Estado estadoNoRecibida = estadoService.obtenerEstadoPorNombreyTipo(nombreNoRecibida, tipo);
            Estado estadoCancelado = estadoService.obtenerEstadoPorNombreyTipo(nombreCancelado, tipo);

            if (Utileria.nonEmptyList(citas)) {
                for (Cita cita : citas) {

                    if (cita.getEstado().getNombre().equals(GeneralConstants.ESTADO_CITA_ACEPTADA)
                            && Utileria.fechaAntes(cita.getFechaCita())) {
                        cita.setEstado(estadoNoRecibida);
                    } else if (cita.getEstado().getNombre().equals(GeneralConstants.ESTADO_CITA_PROCESO)
                            && Utileria.fechaAntes(cita.getFechaCita())) {
                        cita.setEstado(estadoCancelado);

                    }
                    citaRepository.save(cita);
                    citasBean.add(Utileria.mapper.map(cita, CitaBean.class));

                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return citasBean;


    }

    @Override
    public CitaBean obtenerInforfmacionCita(Integer id) throws SigetException {
        try {

            Cita cita = obtenerCita(id);

            CitaBean citaBean = Utileria.mapper.map(cita, CitaBean.class);

            citaBean.setDocumentos(new ArrayList<>());

            List<DocumentoAnexo> documentoAnexos = documentoAnexoRepository.findAllByCita(cita);
            if (Utileria.nonEmptyList(documentoAnexos)) {
                for (DocumentoAnexo documentoAnexo : documentoAnexos) {
                    DocumentoBean documentoBean = Utileria.mapper.map(documentoAnexo.getDocumento(), DocumentoBean.class);
                    citaBean.getDocumentos().add(new DocumentoAnexoBean(documentoAnexo.getId(), documentoBean));
                }
            }

            return citaBean;
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public List<Estado> obtenerEstadosCita(Integer id) throws SigetException {
        try {
            List<Estado> estados = new ArrayList<>();

            Cita cita = obtenerCita(id);
            String tipo = GeneralConstants.TIPO_ESTADO_CITA;
            String estadoActual = cita.getEstado().getNombre();
            String aceptada = GeneralConstants.ESTADO_CITA_ACEPTADA;
            String proceso = GeneralConstants.ESTADO_CITA_PROCESO;
            String cancelada = GeneralConstants.ESTADO_CITA_CANCELADA;
            String rebibida = GeneralConstants.ESTADO_CITA_RECIBIDA;
            String noRecibida = GeneralConstants.ESTADO_CITA_NO_RECIBIDA;
            if (estadoActual.equals(proceso)) {
                estados.add(estadoService.obtenerEstadoPorNombreyTipo(aceptada, tipo));
                estados.add(estadoService.obtenerEstadoPorNombreyTipo(cancelada, tipo));
            } else if (estadoActual.equals(aceptada)) {
                estados.add(estadoService.obtenerEstadoPorNombreyTipo(rebibida, tipo));
                estados.add(estadoService.obtenerEstadoPorNombreyTipo(noRecibida, tipo));

            }
            return estados;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Cita obtenerCitaRegistro() {
        Cita cita = new Cita();
        cita.setServicio(new Servicio());
        return cita;
    }

    @Override
    public List<CitaBean> listarCitasReservacion(CitaBean citaBean) {
        try {

            String aceptada = GeneralConstants.ESTADO_CITA_ACEPTADA;
            String proceso = GeneralConstants.ESTADO_CITA_PROCESO;

            List<CitaBean> citaBeans = new ArrayList<>();

            Timestamp fechaCita = citaBean.getFechaCita();
            Servicio servicio = servicioService.obtenerServicio(citaBean.getServicio().getId());

            List<Estado> estados = estadoService.obtenerEstadosCita().stream().filter(estado ->
                    estado.getNombre().equals(aceptada) || estado.getNombre().equals(proceso)
            ).collect(Collectors.toList());


            List<Cita> citas = citaRepository.findAllByServicioAndEstadoIn(servicio, estados);


            for (Cita cita : citas) {
                if (cita.getFechaCita().equals(fechaCita)) {
                    citaBeans.add(new CitaBean(cita.getHoraInicio(), cita.getHoraFin()));
                }
            }

            return citaBeans;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public String autorizarPago(Integer idServicio) throws SigetException {
        try{
            Servicio servicio = servicioService.obtenerServicio(idServicio);
            PagoPayPalBean pagoPayPalBean = new PagoPayPalBean(servicio.getNombre(), servicio.getCosto().floatValue());
            return  payPalService.autorizarPago(pagoPayPalBean);
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

}
