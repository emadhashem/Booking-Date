package com.book.date.BookingDate.features.members.service;

import com.book.date.BookingDate.features.members.dto.LoginDto;
import com.book.date.BookingDate.features.members.dto.SignupDto;
import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.entity.Role;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.repository.RoleRepo;
import com.book.date.BookingDate.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepo memberRepo;
    private final RoleRepo roleRepo;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailOrPassword(), loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);

    }

    @Override
    public String signup(SignupDto signupDto) throws ResponseStatusException {

        Set<Role> roles = initRoles(signupDto);
        memberRepo.save(
                Member.builder()
                        .name(signupDto.getName())
                        .email(signupDto.getEmail())
                        .password(passwordEncoder.encode(signupDto.getPassword()))
                        .roles(roles)
                        .build());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupDto.getEmail(), signupDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);

    }

    private Set<Role> initRoles(SignupDto signupDto) throws ResponseStatusException {
        Set<Role> roles = new HashSet<>();
        final String role_prefix = "ROLE_";
        roles.add(roleRepo.findByName(role_prefix + "USER").orElseThrow());
        if (signupDto.getRole() != null && signupDto.getRole().equals("VENDOR")) {
            roles.add(roleRepo.findByName(role_prefix + "VENDOR").orElseThrow());
        }
        return roles;
    }
}
