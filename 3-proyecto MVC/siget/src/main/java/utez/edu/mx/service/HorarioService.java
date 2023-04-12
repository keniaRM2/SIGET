package utez.edu.mx.service;

import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.HorarioBean;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Horario;

import java.util.List;

public interface HorarioService {

    List<HorarioBean> validarDisponilidad(CitaBean citaBean) throws SigetException;

    Horario obtenerHorarioAtencionCita(CitaBean citaBean) throws SigetException;
}
