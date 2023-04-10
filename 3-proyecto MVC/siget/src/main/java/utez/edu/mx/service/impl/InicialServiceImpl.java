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

            List<String> dias = Arrays.asList("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
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

            List<Estado> estados = Arrays.asList(
                    new Estado(GeneralConstants.ESTADO_CITA_PROCESO, new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA), "#99A3A4"),
                    new Estado(GeneralConstants.ESTADO_CITA_ACEPTADA, new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA), "#2ECC71"),
                    new Estado(GeneralConstants.ESTADO_CITA_CANCELADA, new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA), "#E74C3C"),
                    new Estado(GeneralConstants.ESTADO_CITA_RECIBIDA, new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA), "#3498DB"),
                    new Estado(GeneralConstants.ESTADO_CITA_NO_RECIBIDA, new TipoEstado(GeneralConstants.TIPO_ESTADO_CITA), "#E67E22"),
                    new Estado(GeneralConstants.ESTADO_PAGO_ACEPTADO, new TipoEstado(GeneralConstants.TIPO_ESTADO_PAGO), "#2ECC71"),
                    new Estado(GeneralConstants.ESTADO_PAGO_RECHAZADO, new TipoEstado(GeneralConstants.TIPO_ESTADO_PAGO), "#E74C3C")
            );


            for(Estado estado : estados){
                TipoEstado tipoEstado = tipoEstadoRepository.findByNombre(estado.getTipoEstado().getNombre());
                Estado busqueda = estadoRepository.findByNombreAndTipoEstado(estado.getNombre(), tipoEstado);
                if(busqueda != null){
                    estado.setId(busqueda.getId());
                }
                estado.setTipoEstado(tipoEstado);
                estadoRepository.save(estado);
            }



            TipoServicio tipoServicioCosto = new TipoServicio(GeneralConstants.TIPO_SERVICIO_COSTO);
            TipoServicio tipoServicioGratuito = new TipoServicio(GeneralConstants.TIPO_SERVICIO_GRATUITO);
            Integer estatusActivo = GeneralConstants.ESTATUS_ACTIVO;

            List<Servicio> servicios = Arrays.asList(
                    new Servicio("Ficha de admisión", "Una vez que se ha obtenido la ficha de admisión, se debe llenar con la información personal y académica requerida.", 1040.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Curso de selección para aspirantes", "El objetivo principal del curso de selección es evaluar a los aspirantes en función de sus conocimientos y competencias para determinar si son aptos para el puesto al que están postulando.", 0.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Inscripción TSU", "Presentación de una solicitud de admisión, la revisión de los requisitos de ingreso", 520.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Inscripción ING-LIC", "Presentación de una solicitud de admisión, la revisión de los requisitos de ingreso a ING-LIC", 390.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cuota cuatrimestral TSU", " Tasa que los estudiantes de este nivel educativo deben pagar para cubrir los gastos de su formación académica", 1900.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cuota cuatrimestral ING-LIC", " Tasa que los estudiantes de este nivel educativo deben pagar para cubrir los gastos de su formación académica", 2200.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Certificación de estudios", "Validar y comprobar la autenticidad de los estudios realizados por una persona en una institución educativa.", 1700.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Exámen extraordinario", "Prueba que permite a los estudiantes que no hayan aprobado una asignatura durante el período escolar regular,", 130.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Constancia de servicio social", "Documento que acredita que un estudiante ha cumplido con el servicio social obligatorio requerido por su institución educativa", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Certificado (parcial)", " Documento que acredite la aprobación de ciertas materias o asignaturas de un programa académico.", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Boleta de calificación sellada", "Se refiere a la obtención de una copia oficial de la boleta de calificaciones de un estudiante, que ha sido verificada y sellada por una institución", 0.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Constancia de estudios", "Documento oficial que certifica la situación académica de un estudiante en una institución educativa.", 90.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Historial académico", "Registro detallado de las calificaciones y los cursos completados por un estudiante durante su trayectoria académica.", 90.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Cotejo y entrega de documentos oficiales", "Proceso en el que  verifica la autenticidad de los documentos presentados por una persona", 0.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Reposición de credencial", " Trámite que se realiza cuando se ha perdido, extraviado o dañado la credencial original", 120.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Trámite de equivalaencia externa", "Permite a una persona que ha estudiado en el extranjero obtener el reconocimiento oficial de sus estudios en el país de destino. ", 520.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Toma de materias por asesoría", "Asesoría que se lleva a cabo en algunas instituciones educativas y universidades, con el fin de brindar a los estudiantes la oportunidad de recibir una atención más personalizada", 0.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Curso de regularización", " Proceso de estudio diseñado para ayudar a los estudiantes a ponerse al día en las materias que no han podido aprobar", 390.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Carta de pesante", " Documento que se emite a los estudiantes que han completado satisfactoriamente su educación superior", 190.0, tipoServicioCosto, estatusActivo),
                    new Servicio("Revisión de reporte de estadía extemporáneo", "Proceso en el que un estudiante presenta su reporte de estadía después de la fecha límite establecida", 0.0, tipoServicioCosto, estatusActivo)
            );

            for (Servicio servicio : servicios) {
                Servicio busqueda = servicioRepository.findByNombre(servicio.getNombre());
                if (Utileria.isNull(busqueda)) {
                    TipoServicio tipoServicio = tipoServicioRepository.findByNombre(servicio.getTipoServicio().getNombre());
                    servicio.setTipoServicio(tipoServicio);
                    servicioRepository.save(servicio);
                }
            }

            List<String> tiposEstado = Arrays.asList(GeneralConstants.TIPO_ESTADO_PAGO, GeneralConstants.TIPO_ESTADO_CITA);
            for (String nombre : tiposEstado) {
                TipoEstado busqueda = tipoEstadoRepository.findByNombre(nombre);
                if (Utileria.isNull(busqueda)) {
                    tipoEstadoRepository.save(new TipoEstado(nombre));
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
