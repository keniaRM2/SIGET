package utez.edu.mx.service;

import utez.edu.mx.core.bean.DocumentoBean;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.dao.model.Documento;

import java.util.List;

public interface DocumentoService {

    public List<DocumentoBean> listarDocumentosPorServicio(Integer idServicio);

    Documento obtenerPorId(Integer id) throws SigetException;
}
