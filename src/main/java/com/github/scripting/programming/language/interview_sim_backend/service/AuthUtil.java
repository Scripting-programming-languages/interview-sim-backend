package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.entity.User;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    /**
     * Получает идентификатор текущего аутентифицированного пользователя из контекста Spring Security.
     * Если пользователь не аутентифицирован, выбрасывается исключение BaseApiException с кодом 403.
     *
     * @return id пользователя
     * @throws BaseApiException, если пользователь не аутентифицирован или principal не является экземпляром User
     */
    @NotNull
    public Long getCurrentUserId() {
        var authentification = SecurityContextHolder.getContext().getAuthentication();
        if (authentification == null || !authentification.isAuthenticated()) {
            throw new BaseApiException(HttpStatus.UNAUTHORIZED, "Пользователь не аутентифицирован");
        }
        var userDetails = (UserDetails) authentification.getPrincipal();
        if (userDetails instanceof User user) {
            return user.getId();
        }
        throw new BaseApiException(HttpStatus.UNAUTHORIZED, "Пользователь не аутентифицирован");
    }
}
