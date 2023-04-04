package utez.edu.mx.core.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
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
        return "Ocurrió un error al acceder a la información.";
    }
}
