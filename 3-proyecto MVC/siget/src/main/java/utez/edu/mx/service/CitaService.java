package utez.edu.mx.service;

import utez.edu.mx.core.bean.ArchivoBean;
import utez.edu.mx.core.bean.CitaBean;
import utez.edu.mx.core.bean.CitaRegistroBean;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Cita;
import utez.edu.mx.dao.model.Estado;

import java.util.List;

public interface CitaService {

    Cita obtenerCita(Integer id) throws SigetException;

    Integer guardar(CitaRegistroBean cita) throws SigetException;

    void cambiarEstadoCita(CitaBean cita) throws SigetException;

    List<CitaBean> listarCitas();


    CitaBean obtenerInforfmacionCita(Integer id) throws SigetException;

    List<Estado> obtenerEstadosCita(Integer id) throws SigetException;

    Cita obtenerCitaRegistro();

    List<CitaBean> listarCitasReservacion(CitaBean citaBean);

    String autorizarPago(Integer idServicio) throws SigetException;

    void realizarPago(String idPago, String idEmisor) throws SigetException;

    ArchivoBean obtenerArchivoAnexo(Integer id) throws SigetException;

    ArchivoBean obtenerComprobantePago(Integer id) throws SigetException;

}
