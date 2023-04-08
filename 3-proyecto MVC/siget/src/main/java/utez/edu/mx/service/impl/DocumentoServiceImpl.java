package utez.edu.mx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.bean.DocumentoBean;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.Documento;
import utez.edu.mx.dao.model.Servicio;
import utez.edu.mx.dao.repository.DocumentoRepository;
import utez.edu.mx.service.DocumentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;


    @Override
    public List<DocumentoBean> listarDocumentosPorServicio(Integer idServicio) {
        try {
            List<Documento> documentos = documentoRepository.findAllByServicio(new Servicio(idServicio));
            return documentos.stream().map(documento -> Utileria.mapper.map(documento, DocumentoBean.class)).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
