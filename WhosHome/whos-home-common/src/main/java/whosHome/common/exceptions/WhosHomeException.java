package whosHome.common.exceptions;

public class WhosHomeException extends RuntimeException {
    public WhosHomeException(String message) {
        super(message);
    }

    public WhosHomeException(String message, Throwable cause) {
        super(message, cause);
    }
}
