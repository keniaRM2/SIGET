package utez.edu.mx.service;

import utez.edu.mx.dao.model.Rol;
import utez.edu.mx.dao.model.Usuario;

public interface UsuarioService {


    public Usuario obtenerUsuarioSesion ();
    public Rol obtenerRolSesion() throws Exception;



}
