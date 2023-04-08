package utez.edu.mx.core.exceptions;

public class SigetException extends RuntimeException {

    private static final long serialVersionUID = 1125442070391271558L;
    private final String message;

    public SigetException(String message) {
        super(message);
        this.message = message;
    }

    public SigetException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
