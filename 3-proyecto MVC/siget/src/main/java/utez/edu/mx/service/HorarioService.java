package utez.edu.mx.service;

import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.HorarioBean;
import utez.edu.mx.core.exceptions.SigetException;

import java.util.List;

public interface HorarioService {
    List<HorarioBean> validarDisponilidad(CitaBean citaBean) throws SigetException;
}
