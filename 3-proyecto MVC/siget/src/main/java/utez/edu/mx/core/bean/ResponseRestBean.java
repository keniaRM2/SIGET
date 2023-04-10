package utez.edu.mx.core.bean;


import lombok.Data;

import java.util.List;

@Data
public class ResponseRestBean<KENINI> {

    private boolean respuesta;
    private String mensaje;
    private KENINI objeto;
    private List<KENINI> objetos;

    public ResponseRestBean(Boolean respuesta, KENINI objeto) {
        this.respuesta = respuesta;
        this.objeto = objeto;
    }

    public ResponseRestBean(String mensaje) {
        this.mensaje = mensaje;
    }

    public ResponseRestBean(boolean respuesta, List<KENINI> objetos) {
        this.respuesta = respuesta;
        this.objetos = objetos;
    }
}
