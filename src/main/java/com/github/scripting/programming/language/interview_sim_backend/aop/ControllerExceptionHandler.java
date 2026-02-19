package com.github.scripting.programming.language.interview_sim_backend.aop;

import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.model.BaseError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Перехватывает исключения {@link BaseApiException}
     *
     * @param baseApiException
     * @return
     */
    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<BaseError> handleBaseApiException(BaseApiException baseApiException) {
        return ResponseEntity.status(baseApiException.getStatusCode())
                .contentType(APPLICATION_JSON)
                .body(new BaseError().error(baseApiException.getMessage()));
    }

    /**
     * Перехватывает {@link AccessDeniedException}
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(UNAUTHORIZED)
                .build();
    }

    /**
     * Перехватывает {@link ConstraintViolationException}
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Void> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .build();
    }


    /**
     * Остальные не перехваченные исключения
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .build();
    }
}
