package co.bitshifted.snapfx.error;

public class ViewLoadException extends RuntimeException{

    public ViewLoadException(Throwable cause) {
        super(cause);
    }

    public ViewLoadException(String message) {
        super(message);
    }
}
