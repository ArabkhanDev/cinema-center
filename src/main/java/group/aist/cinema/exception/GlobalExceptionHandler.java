package group.aist.cinema.exception;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    @SneakyThrows
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException exception, WebRequest request) {
        log.info("Method argument not valid exception occurred {}", request);
        List<ConstraintsViolationError> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return ofType(request, HttpStatus.BAD_REQUEST, "You are missing some fields", validationErrors);
    }

    @SneakyThrows
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handle(HttpMessageNotReadableException exception, WebRequest request) {
        log.info("Http message is missing request body");
        return ofType(request, HttpStatus.BAD_REQUEST, "You are missing request body", null);
    }

    @SneakyThrows
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handle(DataAccessException exception, WebRequest request) {
        log.error("Database error occurred", exception);
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurred", null);
    }

    @SneakyThrows
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handle(NotFoundException exception, WebRequest request) {
        log.error("Not found error occurred", exception);
        return ofType(request, HttpStatus.NOT_FOUND, exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handle(RuntimeException exception, WebRequest request) {
        log.error("Element not found", exception);
        return ofType(request, HttpStatus.BAD_REQUEST, exception.getMessage(), null);
    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handle(Exception exception, WebRequest request) {
        log.error("An unexpected error occurred", exception);
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status,
                                                       String message,
                                                       List<ConstraintsViolationError> validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put("status", status.value());
        attributes.put("error", message);
        attributes.put("fieldErrors", validationErrors);
        attributes.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }
}
