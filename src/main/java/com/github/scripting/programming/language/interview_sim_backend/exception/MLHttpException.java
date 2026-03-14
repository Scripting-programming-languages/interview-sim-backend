package com.github.scripting.programming.language.interview_sim_backend.exception;

import org.springframework.http.HttpStatusCode;

public class MLHttpException extends RuntimeException {
    private static final String MSG = "ML Service http error: ";

    public MLHttpException(HttpStatusCode statusCode) {
        super(MSG + statusCode);
    }
}
