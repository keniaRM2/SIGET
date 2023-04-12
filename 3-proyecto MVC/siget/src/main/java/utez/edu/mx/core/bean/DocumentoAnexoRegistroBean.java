package utez.edu.mx.core.bean;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentoAnexoRegistroBean extends DocumentoAnexoBean {

    private MultipartFile documentoAnexo;

}
