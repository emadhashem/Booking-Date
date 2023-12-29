package com.book.date.BookingDate.config;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final MemberRepo memberRepo;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return usernameOrEmail -> {
            Member member = memberRepo
                    .findByEmailOrName(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            Set<GrantedAuthority> authorities = member
                    .getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails.User(
                    usernameOrEmail, member.getPassword(), authorities
            );
        };

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
