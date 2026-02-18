package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.config.properties.JwtProperties;
import com.github.scripting.programming.language.interview_sim_backend.entity.Token;
import com.github.scripting.programming.language.interview_sim_backend.entity.User;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.exception.UnauthorizedExcpetion;
import com.github.scripting.programming.language.interview_sim_backend.repository.TokenRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.UserRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.AuthService;
import com.github.scripting.programming.language.interview_sim_backend.service.JwtService;
import com.github.scripting.programming.language.model.AuthResponse;
import com.github.scripting.programming.language.model.UserLoginRequest;
import com.github.scripting.programming.language.model.UserRegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    private static @NonNull ZonedDateTime getExpiryDate(Long refreshTokenExpiration) {
        return ZonedDateTime.now().plusNanos(refreshTokenExpiration * 1_000_000);
    }

    @Override
    @Transactional
    public AuthResponse login(UserLoginRequest userLoginRequest) {
        var user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(UnauthorizedExcpetion::new);
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedExcpetion();
        }
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveToken(refreshToken, user, jwtProperties.refreshTokenExpiration());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BaseApiException(BAD_REQUEST, "Email уже существует");
        }
        var user = saveUser(request);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveToken(refreshToken, user, jwtProperties.refreshTokenExpiration());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(final String refreshToken) {
        Long userId = jwtService.extractUserId(refreshToken)
                .orElseThrow(() -> new BaseApiException(UNAUTHORIZED, "Refresh token не валиден"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseApiException(UNAUTHORIZED, "Пользователя не существует"));
        if (!jwtService.isValid(refreshToken, user)) {
            throw new BaseApiException(UNAUTHORIZED, "Refresh token не валиден");
        }
        var token = tokenRepository.findByUserIdAndRefreshToken(userId, refreshToken)
                .filter(t -> !t.isRevoked())
                .filter(t -> t.getExpiryDate().isAfter(ZonedDateTime.now()))
                .orElseThrow(() -> new BaseApiException(UNAUTHORIZED, "Refresh token не существует"));

        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        updateRefreshToken(token, newRefreshToken);
        return new AuthResponse(accessToken, newRefreshToken);
    }

    private void updateRefreshToken(Token token, String newRefreshToken) {
        token.setRefreshToken(newRefreshToken);
        token.setExpiryDate(getExpiryDate(jwtProperties.refreshTokenExpiration()));

        tokenRepository.save(token);
    }

    private void saveToken(String refreshToken, User user, Long refreshTokenExpiration) {
        ZonedDateTime expiryDate = getExpiryDate(refreshTokenExpiration);

        Token tokenEntity = Token.builder()
                .refreshToken(refreshToken)
                .user(user)
                .expiryDate(expiryDate)
                .revoked(false)
                .build();

        tokenRepository.save(tokenEntity);
    }

    private @NonNull User saveUser(UserRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthdate(request.getBirthdate())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(user);
    }
}
