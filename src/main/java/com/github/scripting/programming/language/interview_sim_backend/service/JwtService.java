package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface JwtService {
    boolean isValid(String token, UserDetails userDetails);

    boolean isValidRefresh(String token, UserDetails user);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUsername(String token);

    Optional<Long> extractUserId(String token);
}
