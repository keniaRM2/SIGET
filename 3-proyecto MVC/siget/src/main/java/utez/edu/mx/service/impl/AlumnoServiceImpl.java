package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.ConstraintViolationException;
import utez.edu.mx.core.constants.GeneralConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.Alumno;
import utez.edu.mx.dao.model.Carrera;
import utez.edu.mx.dao.model.Persona;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.dao.repository.AlumnoRepository;
import utez.edu.mx.dao.repository.PersonaRepository;
import utez.edu.mx.dao.repository.RolRepository;
import utez.edu.mx.dao.repository.UsuarioRepository;
import utez.edu.mx.service.AlumnoService;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService {


    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll(Sort.by("id").ascending());
    }
    

    @Override
    public void guardar(Alumno alumno) throws SigetException {
        if (Utileria.nonNull(alumno.getId())) {
            actualizar(alumno);
        } else {
            registrar(alumno);
        }
    }

    @Override
    @Transactional
    public void registrar(Alumno alumno) throws SigetException {
        try {

            Alumno alumnoRepetido = alumnoRepository.findByMatricula(alumno.getMatricula());
            if (Utileria.nonNull(alumnoRepetido)) {
                throw new SigetException("La matricula de alumno ha sido registrado previamente");
            }

            Usuario usuario = alumno.getPersona().getUsuario();

            Usuario usuarioRepetido = usuarioRepository.findByUsername(usuario.getUsername());
            if (Utileria.nonNull(usuarioRepetido)) {
                throw new SigetException("Correo electrónico registrado previamente.");
            }
            Persona persona = alumno.getPersona();

            usuario.setRol(rolRepository.findByAuthority(GeneralConstants.ROL_ALUMNO));
            usuario.setEnabled(GeneralConstants.ESTATUS_ACTIVO);
            usuario.setPassword(GeneralConstants.CODIFICAROR.encode(alumno.getMatricula()));
            usuario.setPersona(persona);

            personaRepository.save(persona);
            alumno.setPersona(persona);
            usuario.setPersona(persona);
            usuarioRepository.save(usuario);
            alumnoRepository.save(alumno);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    @Transactional
    public void actualizar(Alumno alumno) throws SigetException {
        try {

            Optional<Alumno> alumnoOptional = alumnoRepository.findById(alumno.getId());
            if (alumnoOptional.isEmpty()) {
                throw new SigetException("Alumno no encontrado.");
            }

            Alumno alumnoUpdate = alumnoOptional.get();
            Persona personaUpdate = alumnoOptional.get().getPersona(); //No se si esta bien
            Usuario usuario = personaUpdate.getUsuario();


            String matriculaAlumno = alumno.getMatricula();
            Alumno alumnoRepetido = alumnoRepository.findByMatriculaAndIdIsNot(matriculaAlumno, alumnoUpdate.getId());
            if (Utileria.nonNull(alumnoRepetido)) {
                throw new SigetException("El número de alumno ha sido registrado previamente");
            }
            alumnoUpdate.setMatricula(matriculaAlumno);

            String username = alumno.getPersona().getUsuario().getUsername();
            Usuario usuarioRepetido = usuarioRepository.findByUsernameAndIdIsNot(username, usuario.getId());
            if (Utileria.nonNull(usuarioRepetido)) {
                throw new SigetException("Correo electrónico registrado previamente.");
            }
            usuario.setUsername(username);


            personaUpdate.setNombre(alumno.getPersona().getNombre());
            personaUpdate.setPrimerApellido(alumno.getPersona().getPrimerApellido());
            personaUpdate.setSegundoApellido(alumno.getPersona().getSegundoApellido());


            usuarioRepository.save(usuario);
            personaRepository.save(personaUpdate);
            alumnoRepository.save(alumnoUpdate);

        } catch (SigetException e) {
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public Alumno obtenerAlumnoRegistro() {
        Alumno alumno = new Alumno();
        Persona persona = new Persona();
        persona.setUsuario(new Usuario());
        alumno.setPersona(persona);
        alumno.setCarrera( new Carrera());
        return alumno;
    }

    @Override
    public Alumno obtenerAlumnoEdicion(Integer id) throws SigetException {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);
        if (alumnoOptional.isEmpty()) {
            throw new SigetException("Alumno no encontrado.");
        }
        return alumnoOptional.get();
    }

    @Override
    @Transactional
    public void actualizarEstatus(Integer id) throws SigetException {
        try {
            Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);
            if (alumnoOptional.isEmpty()) {
                throw new SigetException("Alumno no encontrado.");
            }

            Alumno alumno = alumnoOptional.get();
            Usuario usuario = alumno.getPersona().getUsuario();
            if (usuario.getEnabled() == GeneralConstants.ESTATUS_ACTIVO) {
                usuario.setEnabled(GeneralConstants.ESTATUS_INACTIVO);
            }else{
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


}
    
