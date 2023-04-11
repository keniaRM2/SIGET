package utez.edu.mx.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.core.bean.PagoPayPalBean;
import utez.edu.mx.core.constants.PathConstants;
import utez.edu.mx.core.exceptions.SigetException;
import utez.edu.mx.core.util.Utileria;
import utez.edu.mx.dao.model.Persona;
import utez.edu.mx.dao.model.Usuario;
import utez.edu.mx.service.PayPalService;
import utez.edu.mx.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import static utez.edu.mx.core.constants.GeneralConstants.*;

/**
 * @author Kenia Reyes Molina
 */
@Service
public class PayPalServiceImpl implements PayPalService {


    @Autowired
    private UsuarioService usuarioService;


    @Override
    public String autorizarPago(PagoPayPalBean pagoPayPalBean) throws SigetException {
        try {

            Payer pagador = obtenerInformacionPagador();
            RedirectUrls redirectUrls = getRedirectURLs();
            List<Transaction> listTransaction = obtenerInformacionTransaccion(pagoPayPalBean);

            Payment solicituPago = new Payment();
            solicituPago.setTransactions(listTransaction);
            solicituPago.setRedirectUrls(redirectUrls);
            solicituPago.setPayer(pagador);
            solicituPago.setIntent(AUTHORIZE);

            APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
            Payment pago = solicituPago.create(apiContext);


            return obtenerUrlProbada(pago);

        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
            throw new SigetException(e.getDetails().getMessage());
        } catch (SigetException e) {
            System.err.println(e.getMessage());
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    @Override
    public void realizarPago(String idPago, String idEmisor) throws SigetException {

        try {
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(idEmisor);

            Payment payment = new Payment().setId(idPago);

            APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

            Payment pago = payment.execute(apiContext, paymentExecution);

            System.out.println(pago.getLinks());
            System.out.println(pago.getLinks());

        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
            throw new SigetException(e.getDetails().getMessage());
        } catch (SigetException e) {
            System.err.println(e.getMessage());
            throw new SigetException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new SigetException(Utileria.getErrorNull());
        }
    }

    private Payer obtenerInformacionPagador() {

        Usuario usuario = usuarioService.obtenerUsuarioSesion();
        Persona persona = usuario.getPersona();

        Payer pagador = new Payer();
        pagador.setPaymentMethod(PAYPAL);

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(persona.getNombre())
                .setLastName(persona.getPrimerApellido())
                .setEmail(usuario.getUsername());

        pagador.setPayerInfo(payerInfo);

        return pagador;
    }

    private RedirectUrls getRedirectURLs() {
        String url = "http://localhost/siget";
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(url+PathConstants.REGISTRAR_CITA);
        redirectUrls.setReturnUrl(url+PathConstants.REALIZAR_PAGO);
        return redirectUrls;
    }

    private List<Transaction> obtenerInformacionTransaccion(PagoPayPalBean pago) {

        String total = Utileria.formatoPagoPayPal(pago.getTotal());
        String costoCero = Utileria.formatoPagoPayPal(0F);

        System.out.println("total : " + total);

        Details detalles = new Details();
        detalles.setShipping(costoCero);
        detalles.setTax(costoCero);
        detalles.setSubtotal(total);

        Amount monto = new Amount();
        monto.setCurrency(MXN);
        monto.setTotal(total);
        monto.setDetails(detalles);

        Transaction transaccion = new Transaction();
        transaccion.setAmount(monto);
        transaccion.setDescription(pago.getProducto());

        ItemList listaPago = new ItemList();
        List<Item> productos = new ArrayList<>();

        Item item = new Item();
        item.setCurrency(MXN);
        item.setName(pago.getProducto());
        item.setPrice(total);
        item.setTax(costoCero);
        item.setQuantity(CANTIDAD_PAGO);

        productos.add(item);
        listaPago.setItems(productos);
        transaccion.setItemList(listaPago);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaccion);

        return listTransaction;
    }

    private String obtenerUrlProbada(Payment pago) {
        List<Links> links = pago.getLinks();
        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase(URL_APROBADO)) {
                return link.getHref();
            }
        }
        return null;
    }
}
