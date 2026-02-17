package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.exception.UnauthorizedExcpetion;
import com.github.scripting.programming.language.model.AuthResponse;
import com.github.scripting.programming.language.model.UserLoginRequest;
import com.github.scripting.programming.language.model.UserRegisterRequest;

public interface AuthService {
    /**
     * Выполняет вход пользователя в систему. Проверяет существование пользователя и валидность пароля
     *
     * @param userLoginRequest -- входные параметры для идентификации пользователя
     * @return {@link AuthResponse} с id пользователя
     * @throws UnauthorizedExcpetion, если пользователя нет или пароль не валиден
     */
    AuthResponse login(UserLoginRequest userLoginRequest);

    /**
     * Регистрирует нового пользователя.
     * Перед сохранением хеширует пароль и проверяет email на уникальность.
     *
     * @param userRegisterRequest DTO с данными нового пользователя.
     * @return {@link AuthResponse} с данными созданного пользователя и токеном.
     * @throws BaseApiException, если email уже имеется
     */
    AuthResponse register(UserRegisterRequest userRegisterRequest);
}
