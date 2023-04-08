package utez.edu.mx.controller.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utez.edu.mx.controller.BaseController;
import utez.edu.mx.core.bean.DocumentoBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.service.DocumentoService;

import java.util.List;

@RestController
public class DocumentoRestController extends BaseController {

    @Autowired
    private DocumentoService documentoService;


    @PostMapping(params = "idServicio", value = PathConstants.LISTAR_DOCUMENTOS_SERVICIO)
    public List<DocumentoBean> listarDocumentosPorServicio(@RequestParam("idServicio") Integer idServicio) {
        return documentoService.listarDocumentosPorServicio(idServicio);
    }



}
