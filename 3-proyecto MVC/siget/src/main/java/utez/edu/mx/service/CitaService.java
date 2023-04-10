package utez.edu.mx.service;

import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.EstadoBean;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Estado;

import java.sql.Timestamp;
import java.util.List;

public interface CitaService {

    Cita obtenerCita(Integer id) throws SigetException;
    void guardar(Cita cita) throws SigetException;

    void cambiarEstadoCita(CitaBean cita) throws SigetException;

    List<CitaBean> listarCitas();


    CitaBean obtenerInforfmacionCita(Integer id) throws SigetException;

    List<Estado> obtenerEstadosCita(Integer id) throws SigetException;

    Cita obtenerCitaRegistro();

    List<CitaBean> listarCitasReservacion(CitaBean citaBean);
}
