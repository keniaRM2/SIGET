package utez.edu.mx.core.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import utez.edu.mx.core.util.Utileria;

import java.util.Date;
import java.util.List;

@Data
public class CitaRegistroBean {

    private Integer idServicio;
    private String fechaCita;
    private String horaInicio;

    private List<MultipartFile> archivosAnexos;
    private MultipartFile comprobantePago;


    public Date getFechaCita() {
        return Utileria.obtenerFechaFormato(fechaCita);
    }

    public Date getHoraInicio() {
        return Utileria.obtenerFechaFormato(horaInicio);
    }
}
