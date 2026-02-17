package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.entity.User;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.exception.UnauthorizedExcpetion;
import com.github.scripting.programming.language.interview_sim_backend.mapper.UserMapper;
import com.github.scripting.programming.language.interview_sim_backend.repository.UserRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.AuthService;
import com.github.scripting.programming.language.model.AuthResponse;
import com.github.scripting.programming.language.model.UserLoginRequest;
import com.github.scripting.programming.language.model.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(UserLoginRequest userLoginRequest) {
        var user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(UnauthorizedExcpetion::new);
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedExcpetion();
        }
        String token = "1"; // TODO: add jwt service
        return userMapper.toAuthResponse(user, token);
    }

    @Override

    public AuthResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BaseApiException(HttpStatus.BAD_REQUEST, "Email уже существует");
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthdate(request.getBirthdate())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        var savedUser = userRepository.save(user);
        String token = "1"; // TODO: add jwt service
        return userMapper.toAuthResponse(savedUser, token);
    }
}
