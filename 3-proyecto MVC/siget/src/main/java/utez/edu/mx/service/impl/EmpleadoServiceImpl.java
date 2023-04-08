package utez.edu.mx.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.*;
import utez.edu.mx.dao.repository.*;
import utez.edu.mx.service.EmpleadoService;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private DiaRepository diaRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public void guardar(Empleado empleado) throws SigetException {
        if (Utileria.nonNull(empleado.getId())) {
            actualizar(empleado);
        } else {
            registrar(empleado);
        }
    }

    @Override
    @Transactional
    public void registrar(Empleado empleado) throws SigetException {
        try {

            Empleado empleadoRepetido = empleadoRepository.findByNumeroEmpleado(empleado.getNumeroEmpleado());
            if (Utileria.nonNull(empleadoRepetido)) {
                throw new SigetException("El número de empleado ha sido registrado previamente");
            }

            Usuario usuario = empleado.getPersona().getUsuario();

            Usuario usuarioRepetido = usuarioRepository.findByUsername(usuario.getUsername());
            if (Utileria.nonNull(usuarioRepetido)) {
                throw new SigetException("Correo electrónico registrado previamente.");
            }
            Persona persona = empleado.getPersona();

            usuario.setRol(rolRepository.findByAuthority(GeneralConstants.ROL_EMPLEADO));
            usuario.setEnabled(GeneralConstants.ESTATUS_ACTIVO);
            usuario.setPassword(GeneralConstants.CODIFICAROR.encode(empleado.getNumeroEmpleado()));
            usuario.setPersona(persona);

            personaRepository.save(persona);
            usuarioRepository.save(usuario);
            empleadoRepository.save(empleado);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    @Transactional
    public void actualizar(Empleado empleado) throws SigetException {
        try {

            Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());
            if (empleadoOptional.isEmpty()) {
                throw new SigetException("Empleado no encontrado.");
            }

            Empleado empleadoUpdate = empleadoOptional.get();
            Persona personaUpdate = empleadoUpdate.getPersona();
            Usuario usuario = personaUpdate.getUsuario();


            String numeroEmpleado = empleado.getNumeroEmpleado();
            Empleado empleadoRepetido = empleadoRepository.findByNumeroEmpleadoAndIdIsNot(numeroEmpleado, empleadoUpdate.getId());
            if (Utileria.nonNull(empleadoRepetido)) {
                throw new SigetException("El número de empleado ha sido registrado previamente");
            }
            empleadoUpdate.setNumeroEmpleado(numeroEmpleado);

            String username = empleado.getPersona().getUsuario().getUsername();
            Usuario usuarioRepetido = usuarioRepository.findByUsernameAndIdIsNot(username, usuario.getId());
            if (Utileria.nonNull(usuarioRepetido)) {
                throw new SigetException("Correo electrónico registrado previamente.");
            }
            usuario.setUsername(username);


            personaUpdate.setNombre(empleado.getPersona().getNombre());
            personaUpdate.setPrimerApellido(empleado.getPersona().getPrimerApellido());
            personaUpdate.setSegundoApellido(empleado.getPersona().getSegundoApellido());


            usuarioRepository.save(usuario);
            personaRepository.save(personaUpdate);
            empleadoRepository.save(empleadoUpdate);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public Empleado obtenerEmpleadoRegistro() {
        Empleado empleado = new Empleado();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());
        empleado.setPersona(persona);
        return empleado;
    }

    @Override
    public Empleado obtenerEmpleadoEdicion(Integer id) throws SigetException {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);
        if (empleadoOptional.isEmpty()) {
            throw new SigetException("Empleado no encontrado.");
        }
        return empleadoOptional.get();
    }

    @Override
    @Transactional
    public void actualizarEstatus(Integer id) throws SigetException {
        try {
            Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);
            if (empleadoOptional.isEmpty()) {
                throw new SigetException("Empleado no encontrado.");
            }

            Empleado empleado = empleadoOptional.get();
            Usuario usuario = empleado.getPersona().getUsuario();
            if (usuario.getEnabled() == GeneralConstants.ESTATUS_ACTIVO) {
                usuario.setEnabled(GeneralConstants.ESTATUS_INACTIVO);
            } else {
                usuario.setEnabled(GeneralConstants.ESTATUS_ACTIVO);
            }
            usuarioRepository.save(usuario);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }

    }

    @Override
    public Empleado obtenerEmpleadoConVentanilla(Cita cita) throws SigetException {
        try {
            String nombreDia = Utileria.obteneDiaSemana(cita.getFechaCita());
            Dia dia = diaRepository.findByNombreIgnoreCase(nombreDia);
            List<Horario> horarios = horarioRepository.findAllByVentanillaAndDiaAndHoraInicioBetween(
                    cita.getVentanilla(),
                    dia,
                    cita.getHoraInicio(),
                    cita.getHoraFin()
            );
            if (Utileria.nonEmptyList(horarios)) {
                return horarios.get(0).getEmpleado();
            }

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }

        return null;
    }


}
