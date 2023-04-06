package utez.edu.mx.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.*;
import utez.edu.mx.service.InicialService;

import java.util.Arrays;
import java.util.List;

@Service
public class InicialServiceImpl implements InicialService {

    @Autowired
    private DiaRepository diaRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private TipoEstadoRepository tipoEstadoRepository;
    @Autowired
    private VentanillaRepository ventanillaRepository;
    @Autowired
    private TipoTelefonoRepository tipoTelefonoRepository;
    @Autowired
    private TipoServicioRepository tipoServicioRepository;
    @Autowired
    private DivisionAcademicaRepository divisionAcademicaRepository;

    @Transactional
    @Override
    public void cargarInformacionInicial() throws RuntimeException {

        try{
            //Registro de roles:
            List<String> authorities = Arrays.asList(GeneralConstants.ROL_ADMIN, GeneralConstants.ROL_EMPLEADO, GeneralConstants.ROL_ALUMNO);
            for (String authority : authorities) {
                Rol rol = rolRepository.findByAuthority(authority);
                if (Utileria.isNull(rol)) {
                    rolRepository.save(new Rol(authority));
                }
            }

            //Registro de tipos de teléfonos:
            List<String> tiposTelefono = Arrays.asList("Personal", "Casa", "Móvil");
            for (String nombre : tiposTelefono) {
                TipoTelefono tipoTelefono = tipoTelefonoRepository.findByNombre(nombre);
                if (Utileria.isNull(tipoTelefono)) {
                    tipoTelefonoRepository.save(new TipoTelefono(nombre));
                }
            }

            List<String> dias = Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
            for (String nombre : dias) {
                Dia dia = diaRepository.findByNombre(nombre);
                if (Utileria.isNull(dia)) {
                    diaRepository.save(new Dia(nombre));
                }
            }

            List<String> ventanillas = Arrays.asList("Ventanilla #1", "Ventanilla #2", "Ventanilla #3");
            for (String nombre : ventanillas) {
                Ventanilla ventanilla = ventanillaRepository.findByNombre(nombre);
                if (Utileria.isNull(ventanilla)) {
                    ventanillaRepository.save(new Ventanilla(nombre));
                }
            }

            List<String> divisionesAcademicas = Arrays.asList("DATIC", "DAMI", "DACEA", "DATEFI");
            for (String nombre : divisionesAcademicas) {
                DivisionAcademica divisionAcademica = divisionAcademicaRepository.findByNombre(nombre);
                if (Utileria.isNull(divisionAcademica)) {
                    divisionAcademicaRepository.save(new DivisionAcademica(nombre));
                }
            }

            List<Carrera> carreras = Arrays.asList(
                    new Carrera("Administración área: Capital Humano", new DivisionAcademica("DACEA")),
                    new Carrera("Desarrollo de negocios área: Mercadotecnia", new DivisionAcademica("DACEA")),
                    new Carrera("Contaduría", new DivisionAcademica("DACEA")),
                    new Carrera("Procesos Industriales área: Manufactura", new DivisionAcademica("DAMI")),
                    new Carrera("Nanotecnología área: Materiales", new DivisionAcademica("DAMI")),
                    new Carrera("Mecatrónica área: Automatización", new DivisionAcademica("DAMI")),
                    new Carrera("Mantenimiento área: Industrial", new DivisionAcademica("DAMI")),
                    new Carrera("Tecnologías de la Información área: Infraestructura de Redes Digitales", new DivisionAcademica("DATIC")),
                    new Carrera("Tecnologías de la Información área: Desarrollo de Software Multiplataforma", new DivisionAcademica("DATIC")),
                    new Carrera("Diseño Digital", new DivisionAcademica("DATIC")),
                    new Carrera("Diseño y moda industrial área: Producción", new DivisionAcademica("DATIC")),
                    new Carrera("Terapia Física área: Rehabilitación", new DivisionAcademica("DATEFI")),
                    new Carrera("Terapia física área: Turismo de Salud y Bienestar", new DivisionAcademica("DATEFI"))
            );

            for (Carrera carrera : carreras) {
                DivisionAcademica divisionAcademica = divisionAcademicaRepository.findByNombre(carrera.getDivisionAcademica().getNombre());
                Carrera busqueda = carreraRepository.findByNombreAndDivisionAcademica(carrera.getNombre(), divisionAcademica);
                if (Utileria.isNull(busqueda)) {
                    carrera.setDivisionAcademica(divisionAcademica);
                    carreraRepository.save(carrera);
                }
            }


            List<String> tiposServicio = Arrays.asList(GeneralConstants.TIPO_SERVICIO_GRATUITO, GeneralConstants.TIPO_SERVICIO_COSTO);
            for (String nombre : tiposServicio) {
                TipoServicio tipoServicio = tipoServicioRepository.findByNombre(nombre);
                if (Utileria.isNull(tipoServicio)) {
                    tipoServicioRepository.save(new TipoServicio(nombre));
                }
            }


            TipoServicio tipoServicioCosto = new TipoServicio(GeneralConstants.TIPO_SERVICIO_COSTO);
            TipoServicio tipoServicioGratuito = new TipoServicio(GeneralConstants.TIPO_SERVICIO_GRATUITO);
            Integer estatusActivo = GeneralConstants.ESTATUS_ACTIVO;

            List<Servicio> servicios = Arrays.asList(
                    new Servicio("Ficha de admisión", "...", 0.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Curso de selección para aspirantes", "...", 700.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Inscripción TSU", "...", 520.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Inscripción ING-LIC", "...", 390.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cuota cuatrimestral TSU", "...", 1900.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cuota cuatrimestral ING-LIC", "...", 2200.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Certificación de estudios", "...", 1700.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Exámen extraordinario", "...", 130.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Constancia de servicio social", "...", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Certificado (parcial)", "...", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Boleta de calificación sellada", "...", 50.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Constancia de estudios", "...", 90.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Historial académico", "...", 90.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cotejo y entrega de documentos oficiales", "...", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Reposición de credencial", "...", 120.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Trámite de equivalaencia externa", "...", 520.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Toma de materias por asesoría", "...", 780.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Curso de regularización", "...", 390.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Carta de pesante", "...", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Revisión de reporte de estadía extemporáneo", "...", 1040.0, tipoServicioCosto, estatusActivo)
            );

            for (Servicio servicio : servicios) {
                Servicio busqueda = servicioRepository.findByNombre(servicio.getNombre());
                if (Utileria.isNull(busqueda)) {
                    TipoServicio tipoServicio = tipoServicioRepository.findByNombre(servicio.getTipoServicio().getNombre());
                    servicio.setTipoServicio(tipoServicio);
                    servicioRepository.save(servicio);
                }
            }

            List<String> tiposEstado = Arrays.asList(GeneralConstants.TIPO_ESTADO_PAGO,
                    GeneralConstants.TIPO_ESTADO_CITA);
            for (String nombre : tiposEstado) {
                TipoEstado busqueda = tipoEstadoRepository.findByNombre(nombre);
                if (Utileria.isNull(busqueda)) {
                    tipoEstadoRepository.save(new TipoEstado(nombre));
                }
            }
            List<Estado> estados = Arrays.asList(
                    new Estado(GeneralConstants.ESTADO_CITA_ACEPTADA,new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA),"#229954"),
                    new Estado(GeneralConstants.ESTADO_CITA_CANCELADA,new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA),"#E74C3C"),
                    new Estado(GeneralConstants.ESTADO_CITA_RECIBIDA,new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA),"#3498DB"),
                    new Estado(GeneralConstants.ESTADO_CITA_NO_RECIBIDA,new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA),"#E67E22"),
                    new Estado(GeneralConstants.ESTADO_CITA_PROCESO,new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA),"#F1C40F"),
                    new Estado(GeneralConstants.ESTADO_PAGO_PAGADO,new TipoEstado(GeneralConstants.TIPO_ESTADO_PAGO),"#229954"),
                    new Estado(GeneralConstants.ESTADO_PAGO_RECHAZADO,new TipoEstado(GeneralConstants.TIPO_ESTADO_PAGO),"#E74C3C")
            );
            for (Estado estado : estados) {
                TipoEstado tipoEstado = tipoEstadoRepository.findByNombre(estado.getTipoEstado().getNombre());
                Estado busqueda = estadoRepository.findByNombreAndTipoEstado(estado.getNombre(), tipoEstado);
                if (Utileria.isNull(busqueda)) {
                    estado.setTipoEstado(tipoEstado);
                    estadoRepository.save(estado);
                }
            }


            Rol rolAdmin = rolRepository.findByAuthority(GeneralConstants.ROL_ADMIN);
            List<Usuario> usuarios = Arrays.asList(
                    new Usuario("admin@gmail.com", "admin",estatusActivo, rolAdmin,
                            new Persona("Administrador", "Reyes", "Molina"))
            );

            for(Usuario usuario : usuarios){
                Usuario busqueda = usuarioRepository.findByUsername(usuario.getUsername());
                if(Utileria.isNull(busqueda)){
                    personaRepository.save(usuario.getPersona());
                    usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
                    usuarioRepository.save(usuario);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }



}
