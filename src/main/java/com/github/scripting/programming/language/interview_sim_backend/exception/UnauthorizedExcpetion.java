package com.github.scripting.programming.language.interview_sim_backend.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedExcpetion extends BaseApiException {
    public UnauthorizedExcpetion() {
        super(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль");
    }
}
