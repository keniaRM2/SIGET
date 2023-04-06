package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;

import java.util.List;

public interface CitaService {
    List<Cita> findAll();
    List<Cita> findByEmployeeId(int id);
    Cita findById(int id);
    boolean save(Cita cita) throws SigetException;
    void cancelCita(int id) throws SigetException;

    List<Cita> listarCitas();
}
