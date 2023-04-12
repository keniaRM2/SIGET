package utez.edu.mx.core.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import utez.edu.mx.core.exceptions.SigetException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Utileria {

    private Utileria() {
    }

    public static boolean  isNull(Object object) {
        return object == null;
    }

    public static boolean nonNull(Object object) {
        return !isNull(object);
    }

    public static  boolean nonEmpty(String value) {
        return nonNull(value) && !value.isEmpty() && !"null".equals(value);
    }

    public static boolean isEmpty(String value) {
        return !nonEmpty(value);
    }

    public static boolean nonEmptyList(List<?> list) {
        return nonNull(list) && !list.isEmpty();
    }

    public static  boolean isEmptyList(List<?> list) {
        return !nonEmptyList(list);
    }

    public static String getErrores(ConstraintViolationException e){
        return e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
    }

    public static String getErrorNull() {
        return "Lo sentimos, ocurrió una incidencia al procesar la información";
    }

    public static Timestamp getFechaHoraActual() {
        return new Timestamp(new Date().getTime());
    }

    public static boolean fechaDespues(Date fecha) {
        Date fechaHoraActual= new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaHoraActual);
        //pinche amlo
        calendar.add(Calendar.HOUR, -1);
        fechaHoraActual=calendar.getTime();
        return fecha.after(fechaHoraActual);
    }


    public static boolean fechaAntes (Date fecha) {
        Date fechaHoraActual= new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaHoraActual);
        calendar.add(Calendar.HOUR, -1);
        fechaHoraActual=calendar.getTime();
        return fecha.before(fechaHoraActual);
    }

    public static String obteneDiaSemana(Date fecha) throws SigetException{
        if(isNull(fecha)){
            throw new SigetException("Fecha no especificada.");
        }
        return new SimpleDateFormat("EEEEE", new Locale("es", "MX")).format(fecha).toLowerCase();
    }

    public static final ModelMapper mapper =  new ModelMapper();

    public static String formatoPagoPayPal(Float total) {
        return String.format("%.2f", total);
    }

    public static Timestamp obtenerHoraFinCita(Timestamp horaInicio) {
        return new Timestamp(new DateTime(horaInicio).plusMinutes(15).toDate().getTime());
    }

    public static Date obtenerFechaFormato(String fecha) {
        if(isEmpty(fecha)){
            return null;
        }
        String[] formatos = {"yyyy-MM-dd HH:mm", "yyyy-MM-dd"};

        for (String formato : formatos){
            Date date = null;
            try{
                 date = new SimpleDateFormat(formato).parse(fecha);
            }catch (ParseException ex){
            }
            if(nonNull(date)){
                return date;
            }
        }

        return null;
    }
}
