package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.core.bean.*;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.CitaRepository;
import utez.edu.mx.dao.repository.ComprobantePagoRepository;
import utez.edu.mx.dao.repository.DocumentoAnexoRepository;
import utez.edu.mx.dao.repository.PagoRepository;
import utez.edu.mx.service.*;

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
    private PagoRepository pagoRepository;

    @Autowired
    private ComprobantePagoRepository comprobantePagoRepository;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private HorarioService horarioService;
    @Override
    public Cita obtenerCita(Integer id) throws SigetException {
        return citaRepository.findById(id).orElseThrow(() -> new SigetException("Cita no encontrada"));
    }

    @Override
    @Transactional
    public Integer guardar(CitaRegistroBean citaBean) throws SigetException {

        try {

            if (Utileria.isNull(citaBean.getFechaCita())) {
                throw new SigetException("Seleccione una fecha valida.");
            }

            if (Utileria.isNull(citaBean.getHoraInicio())) {
                throw new SigetException("Seleccione un horario valido.");
            }

            if (Utileria.isNull(citaBean.getIdServicio())) {
                throw new SigetException("Seleccione un trámite.");
            }

            Servicio servicio = servicioService.obtenerServicio(citaBean.getIdServicio());
            boolean conCosto = servicio.getCosto() > 0;

            if (conCosto && Utileria.isNull(citaBean.getComprobantePago())) {
                throw new SigetException("Ingrese el comprobante de pago.");
            }


            List<DocumentoBean> documentos = documentoService.listarDocumentosPorServicio(citaBean.getIdServicio());
            if (Utileria.nonEmptyList(documentos)) {

                if (Utileria.isEmptyList(citaBean.getArchivosAnexos())) {
                    throw new SigetException("Ingrese los documentos solicitados.");
                }

                if (documentos.size() != citaBean.getArchivosAnexos().size()) {
                    throw new SigetException("Todos los documentos son requeridos.");
                }

            }


            Timestamp fechaCita = new Timestamp(citaBean.getFechaCita().getTime());

            if (Utileria.fechaAntes(fechaCita)) {
                throw new SigetException("Seleccione una fecha posterior.");
            }


            String enProceso = GeneralConstants.ESTADO_CITA_PROCESO;
            String tipoCita = GeneralConstants.TIPO_ESTADO_CITA;
            Estado estadoEnProceso = estadoService.obtenerEstadoPorNombreyTipo(enProceso, tipoCita);

            Timestamp horaInicio = new Timestamp(citaBean.getHoraInicio().getTime());
            Timestamp horaFin = Utileria.obtenerHoraFinCita(horaInicio);


            Horario horario = horarioService.obtenerHorarioAtencionCita(new CitaBean(fechaCita, horaInicio, horaFin));

            Cita cita = new Cita();

            cita.setFechaCita(fechaCita);
            cita.setHoraInicio(horaInicio);
            cita.setHoraFin(horaFin);
            cita.setFechaRegistro(Utileria.getFechaHoraActual());
            cita.setAlumno(usuarioServiceImpl.obtenerAlumnoSesion());
            cita.setServicio(servicio);
            cita.setEstado(estadoEnProceso);
            cita.setEmpleado(horario.getEmpleado());
            cita.setVentanilla(horario.getVentanilla());

            citaRepository.save(cita);

            if (Utileria.nonEmptyList(citaBean.getArchivosAnexos())) {
                List<DocumentoAnexo> documentoAnexos = new ArrayList<>();

                for (MultipartFile anexoBean : citaBean.getArchivosAnexos()) {

                    Documento documento = documentoService.obtenerPorId(Integer.parseInt(anexoBean.getOriginalFilename()));

                    DocumentoAnexo documentoAnexo = new DocumentoAnexo();
                    documentoAnexo.setCita(cita);
                    documentoAnexo.setDocumento(documento);
                    documentoAnexo.setDocumentoAnexo(anexoBean.getBytes());

                    documentoAnexos.add(documentoAnexo);
                }

                documentoAnexoRepository.saveAll(documentoAnexos);

            }


            String aceptado = GeneralConstants.ESTADO_PAGO_ACEPTADO;
            String tipoPago = GeneralConstants.TIPO_ESTADO_PAGO;
            Estado estadoAceptado = estadoService.obtenerEstadoPorNombreyTipo(aceptado, tipoPago);

            if(conCosto){
                Pago pago = new Pago();
                pago.setCita(cita);
                pago.setMonto(servicio.getCosto());
                pago.setEstado(estadoAceptado);

                pagoRepository.save(pago);


                ComprobantePago comprobantePago = new ComprobantePago();
                comprobantePago.setPago(pago);
                comprobantePago.setComprobante(citaBean.getComprobantePago().getBytes());
                comprobantePagoRepository.save(comprobantePago);
            }

            return cita.getId();
        } catch (SigetException e) {
            System.out.println(e.getMessage());
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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
            if (nombreNuevoEstado.equals(aceptada)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(cita.getFechaCita());
                // obtener día, mes y año sin horas
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int anio = calendar.get(Calendar.YEAR);

                String fecha = dia + "/" + mes + "/" + anio;
                String msgBody = "Buen día " + cita.getAlumno().getPersona().getNombre() + " " + cita.getAlumno().getPersona().getPrimerApellido() + " " + cita.getAlumno().getPersona().getSegundoApellido() + ", su cita ha sido aceptada para el día , " + fecha + " a la hora : " + cita.getHoraInicio() + " puede consultar su estado directamente en la pagina SIGET, deberá llegar con 15 minutos de anticipación.";
                emailService.sendSimpleMail(new EmailDetails(recipient, msgBody, "Cita aceptada"));
            } else if (nombreNuevoEstado.equals(cancelada)) {
                String msgBody = "Buen día " + cita.getAlumno().getPersona().getNombre() + " " + cita.getAlumno().getPersona().getPrimerApellido() + " " + cita.getAlumno().getPersona().getSegundoApellido() + ", su cita ha sido cancelada, para más detalles puede consultar su estado directamente en ventanilla de manera presencial o en la plataforma SIGET.";
                emailService.sendSimpleMail(new EmailDetails(recipient, msgBody, "Cita cancelada"));
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

                    cita.getEmpleado().setHorarios(null);
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

            Pago pago = pagoRepository.findByCita(cita);
            if (Utileria.nonNull(pago)) {
                citaBean.setPago(new PagoBean(pago.getId()));
            }

            if (Utileria.nonNull(citaBean.getEmpleado())) {
                citaBean.getEmpleado().setHorarios(null);
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
        try {
            Servicio servicio = servicioService.obtenerServicio(idServicio);
            PagoPayPalBean pagoPayPalBean = new PagoPayPalBean(servicio.getNombre(), servicio.getCosto().floatValue());
            return payPalService.autorizarPago(pagoPayPalBean);
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public void realizarPago(String idPago, String idEmisor) throws SigetException {
        try {
            payPalService.realizarPago(idPago, idEmisor);
        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public ArchivoBean obtenerArchivoAnexo(Integer id) throws SigetException {
        ArchivoBean archivoBean = new ArchivoBean();
        try {

            DocumentoAnexo documentoAnexo = documentoAnexoRepository.findById(id).orElseThrow(() -> new SigetException("Archivo no encontrado"));

            archivoBean.setNombre(documentoAnexo.getDocumento().getNombre() + GeneralConstants.PDF_FORMATO);
            archivoBean.setArchivo(documentoAnexo.getDocumentoAnexo());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return archivoBean;
    }

    @Override
    public ArchivoBean obtenerComprobantePago(Integer id) throws SigetException {
        ArchivoBean archivoBean = new ArchivoBean();
        try {

            Pago pago = pagoRepository.findById(id).orElseThrow(() -> new SigetException("Archivo no encontrado"));
            ComprobantePago comprobantePago = comprobantePagoRepository.findByPago(pago);
            archivoBean.setNombre(GeneralConstants.COMPROBANTE_PAGO + GeneralConstants.PDF_FORMATO);
            archivoBean.setArchivo(comprobantePago.getComprobante());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return archivoBean;
    }
}