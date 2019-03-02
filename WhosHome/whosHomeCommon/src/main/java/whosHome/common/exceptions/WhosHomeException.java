package whosHome.common.exceptions;

public class WhosHomeException extends RuntimeException {
    protected static final int DEFAULT_HTTP_STATUS = 500;

    protected int _httpStatus = 500;

    public WhosHomeException(String message) {
        this(message, DEFAULT_HTTP_STATUS, null);
    }

    public WhosHomeException(String message, Throwable cause) {
        this(message, DEFAULT_HTTP_STATUS, cause);
    }

    public WhosHomeException(String message, int httpStatus) {
        this(message, httpStatus, null);
    }

    public WhosHomeException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
        this._httpStatus = httpStatus;
    }

    public int httpStatus() {
        return 500;
    }
}
