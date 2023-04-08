package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> listarServicios();

    void guardar(Servicio servicio) throws SigetException;
    void registrar(Servicio servicio) throws SigetException;
    void actualizar(Servicio servicio) throws SigetException;

    Servicio obtenerServicioRegistro();
    Servicio obtenerServicioEdicion(Integer id) throws SigetException;

    void actualizarEstatus(Integer id) throws SigetException;
}
