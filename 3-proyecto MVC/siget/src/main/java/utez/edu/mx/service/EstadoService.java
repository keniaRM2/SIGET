package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Estado;

import java.util.List;

public interface EstadoService {

    Estado obtenerEstadoPorNombreyTipo(String nombreEstado, String nombreTipo) throws SigetException;

    Estado obtenerPorId(Integer id) throws SigetException;

    List<Estado> obtenerEstadosCita() throws SigetException;
}
