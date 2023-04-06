package utez.edu.mx.service;

import java.util.List;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Alumno;

public interface AlumnoService {
    List<Alumno> listarAlumnos();

    public void guardar(Alumno alumno) throws SigetException;
    public void registrar(Alumno alumno) throws SigetException;
    public void actualizar(Alumno alumno) throws SigetException;

    Alumno obtenerAlumnoRegistro();
    Alumno obtenerAlumnoEdicion(Integer id) throws SigetException;
    public void actualizarEstatus(Integer id) throws SigetException;
    
    
}
