package whosHome.whosHomeApp.engine.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import whosHome.common.exceptions.WhosHomeException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class WhosHomeEngineException extends WhosHomeException {
    public WhosHomeEngineException(String message) {
        super(message);
    }

    public WhosHomeEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
