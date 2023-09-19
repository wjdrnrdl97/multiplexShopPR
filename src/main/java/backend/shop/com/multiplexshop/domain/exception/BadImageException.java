package backend.shop.com.multiplexshop.domain.exception;

public class BadImageException extends RuntimeException {
    public BadImageException() {
        super();
    }

    public BadImageException(String message) {
        super(message);
    }

    public BadImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadImageException(Throwable cause) {
        super(cause);
    }

    protected BadImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
