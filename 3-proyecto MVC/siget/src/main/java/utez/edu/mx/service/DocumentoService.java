package utez.edu.mx.service;

import utez.edu.mx.core.bean.DocumentoBean;

import java.util.List;

public interface DocumentoService {

    public List<DocumentoBean> listarDocumentosPorServicio(Integer idServicio);
}
