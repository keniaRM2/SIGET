package utez.edu.mx.service;

import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Dia;
import utez.edu.mx.dao.model.Horario;
import utez.edu.mx.dao.model.Ventanilla;

import java.sql.Timestamp;
import java.util.List;

public interface HorarioEmpleadoService {
    List<Horario> listarHorario();
    List<Dia> listarDias();
    List<Timestamp> listarHoras();

    void guardar(Horario horario) throws SigetException;


    void registrar(Horario horario) throws SigetException;

    void actualizar(Horario horario) throws SigetException;

    void borrarHorario(Integer id) throws SigetException;

    Horario obtenerHorarioRegistro();







}
