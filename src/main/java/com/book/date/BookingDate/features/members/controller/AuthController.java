package com.book.date.BookingDate.features.members.controller;

import com.book.date.BookingDate.features.members.dto.JwtAuthResponse;
import com.book.date.BookingDate.features.members.dto.LoginDto;
import com.book.date.BookingDate.features.members.dto.SignupDto;
import com.book.date.BookingDate.features.members.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signup(@RequestBody SignupDto signupDto) throws BadRequestException {
        String token = authService.signup(signupDto);
        return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(new JwtAuthResponse(token, "Bearer"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleBadRequestException(Throwable e) {
        Map<String, Object> mp = new HashMap<>();
        if(e.getMessage().contains("duplicate")) {
            mp.put("message", "User already exists");
            return ResponseEntity.badRequest().body(mp);
        }
        mp.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(mp);
    }


}
