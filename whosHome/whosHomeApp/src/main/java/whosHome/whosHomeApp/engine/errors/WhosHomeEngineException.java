package whosHome.whosHomeApp.engine.errors;

import whosHome.common.exceptions.WhosHomeException;

public class WhosHomeEngineException extends WhosHomeException {
    public WhosHomeEngineException(String message) {
        super(message);
    }

    public WhosHomeEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public WhosHomeEngineException withHttpStatus(int httpStatus) {
        _httpStatus = httpStatus;
        return this;
    }
}
