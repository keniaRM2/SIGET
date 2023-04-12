package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    List<Empleado> listarEmpleados();

    void guardar(Empleado empleado) throws SigetException;

    void registrar(Empleado empleado) throws SigetException;

    void actualizar(Empleado empleado) throws SigetException;

    Empleado obtenerEmpleadoRegistro();

    Empleado obtenerEmpleadoEdicion(Integer id) throws SigetException;

    void actualizarEstatus(Integer id) throws SigetException;

}