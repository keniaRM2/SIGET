package utez.edu.mx.core.bean;


import lombok.Data;

@Data
public class DocumentoAnexoBean {

    private Integer id;
    private DocumentoBean documento;

    public DocumentoAnexoBean() {
    }

    public DocumentoAnexoBean(Integer id, DocumentoBean documento) {
        this.id = id;
        this.documento = documento;
    }
}
