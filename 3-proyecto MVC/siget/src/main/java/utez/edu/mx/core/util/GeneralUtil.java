package utez.edu.mx.core.util;

import java.util.List;

public class GeneralUtil {

    private GeneralUtil() {
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


}
