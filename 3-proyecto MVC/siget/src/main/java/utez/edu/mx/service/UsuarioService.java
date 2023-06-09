package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Alumno;
import utez.edu.mx.dao.model.Empleado;
import utez.edu.mx.dao.model.Rol;
import utez.edu.mx.dao.model.Usuario;

public interface UsuarioService {


    public Usuario obtenerUsuarioSesion ();
    public Rol obtenerRolSesion() throws Exception;

    public Alumno obtenerAlumnoSesion () throws SigetException;

    Boolean existeUsuario(String username);

    Usuario obtenerUsuarioPorUsername(String username);

    void actualizarUsuario(Usuario usuario);

    Empleado obtenerEmpleadoSesion() throws SigetException;
}
