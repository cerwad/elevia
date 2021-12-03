package fr.baralecorp.elevia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class MyExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "Please enter a valid value"), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, ex.getMessage()), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(getBody(FORBIDDEN, ex, ex.getMessage()), new HttpHeaders(), FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>(getBody(INTERNAL_SERVER_ERROR, ex, "Something Went Wrong"), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    public Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {

        log.error(message, ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex.toString());

        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }
        return body;
    }

}