package com.github.scripting.programming.language.interview_sim_backend.config.filter;

import com.github.scripting.programming.language.interview_sim_backend.service.JwtService;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtServerInterceptor implements ServerInterceptor {

    // Ключ для передачи userId внутри текущего потока (Context)
    public static final Context.Key<Long> USER_ID_CONTEXT_KEY = Context.key("userId");
    // Стандартный ключ для метаданных
    private static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String rawToken = headers.get(AUTHORIZATION_METADATA_KEY);

        if (rawToken == null || !rawToken.startsWith("Bearer ")) {
            call.close(Status.UNAUTHENTICATED.withDescription("Missing or invalid token"), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }

        String token = rawToken.substring(7);
        var userIdOpt = jwtService.extractUserId(token);
        String username = jwtService.extractUsername(token);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userIdOpt.isEmpty() || !jwtService.isValid(token, userDetails)) {
                call.close(Status.UNAUTHENTICATED.withDescription("Token is invalid or expired"), new Metadata());
                return new ServerCall.Listener<>() {
                };
            }
        } catch (Exception e) {
            call.close(Status.UNAUTHENTICATED.withDescription("Token is invalid or expired"), new Metadata());
            return new ServerCall.Listener<>() {
            };
        }

        // Кладем userId в контекст gRPC, чтобы сервис мог его прочитать
        Context context = Context.current().withValue(USER_ID_CONTEXT_KEY, userIdOpt.get());

        return Contexts.interceptCall(context, call, headers, next);
    }
}