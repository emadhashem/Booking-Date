package com.book.date.BookingDate.config;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String email;
        if (isBearerFound(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.split(" ")[1];
        email = jwtService.extractUsername(jwtToken);
    }

    private boolean isBearerFound(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}
