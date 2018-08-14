package whosHome.whosHomeApp.infra;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import whosHome.common.exceptions.WhosHomeException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(value = {WhosHomeException.class})
    protected ResponseEntity handleWhosHomeException(WhosHomeException ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.httpStatus());
        logger.error(String.format("HTTP Status: %d", ex.httpStatus()), ex);
        return ResponseEntity.status(httpStatus).body(ex.getMessage());
    }
}
