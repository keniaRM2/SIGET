package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;

import java.sql.Timestamp;
import java.util.List;

public interface CitaService {

   void guardar(Cita cita) throws SigetException;
    void cambiarEstadoCita(Cita cita) throws SigetException;
    List<Cita> listarCitas();





}
