package utez.edu.mx.core.bean;

import lombok.Data;

@Data
public class PagoPayPalBean {

    private String producto;
    private Float total;

    public PagoPayPalBean() {
    }

    public PagoPayPalBean(String producto, Float total) {
        this.producto = producto;
        this.total = total;
    }
}
