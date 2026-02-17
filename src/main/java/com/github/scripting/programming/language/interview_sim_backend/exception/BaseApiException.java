package com.github.scripting.programming.language.interview_sim_backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseApiException extends RuntimeException {
    private final HttpStatus statusCode;

    public BaseApiException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
