package utez.edu.mx.service;

import utez.edu.mx.core.bean.PagoPayPalBean;
import utez.edu.mx.core.exceptions.SigetException;

/**
 * @author Kenia Reyes Molina
 * */
public interface PayPalService {

    /**
     * @author Kenia Reyes Molina
     * */
    public String autorizarPago(PagoPayPalBean pagoPayPalBean) throws SigetException;
}
