package whosHome.whosHomeApp.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import whosHome.common.exceptions.WhosHomeException;
import whosHome.whosHomeApp.engine.errors.WhosHomeEngineException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {WhosHomeException.class})
    protected ResponseEntity handleWhosHomeException(WhosHomeException ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.httpStatus());
        return ResponseEntity.status(httpStatus).body(ex.getMessage());
    }
}
