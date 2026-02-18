package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.AuthApi;
import com.github.scripting.programming.language.interview_sim_backend.service.AuthService;
import com.github.scripting.programming.language.model.AuthResponse;
import com.github.scripting.programming.language.model.UserLoginRequest;
import com.github.scripting.programming.language.model.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthApi {
    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> authLoginPost(UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.login(userLoginRequest));
    }

    @Override
    public ResponseEntity<AuthResponse> authRefreshTokenPost(String authorizationToken) {
        String refreshToken = authorizationToken.substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @Override
    public ResponseEntity<AuthResponse> authRegisterPost(UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authService.register(userRegisterRequest));
    }
}
