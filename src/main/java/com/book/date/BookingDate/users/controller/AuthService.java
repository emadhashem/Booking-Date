package com.book.date.BookingDate.users.controller;

import com.book.date.BookingDate.config.JwtService;
import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;
import com.book.date.BookingDate.users.repository.RoleRepo;
import com.book.date.BookingDate.users.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        var user = memberService.getMemberByEmail(loginRequest.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse singup(SignupRequest signupRequest) {
        var user = Member.builder().email(signupRequest.getEmail()).userName(signupRequest.getUserName()).password(passwordEncoder.encode(signupRequest.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName("ROLE_USER"));
        if (signupRequest.getRole() != null &&
                signupRequest.getRole().equals("ROLE_VENDOR")) {
            roles.add(roleRepo.findByName("ROLE_VENDOR"));
        }
        var user2 = user.roles(roles).build();
        memberService.save(user2);
        var jwtToken = jwtService.generateToken(user2);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}