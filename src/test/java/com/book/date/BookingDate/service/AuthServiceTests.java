package com.book.date.BookingDate.service;

import com.book.date.BookingDate.features.members.dto.LoginDto;
import com.book.date.BookingDate.features.members.dto.SignupDto;
import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.entity.Role;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.repository.RoleRepo;
import com.book.date.BookingDate.features.members.service.AuthServiceImpl;
import com.book.date.BookingDate.provider.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepo memberRepo;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    void test_login() {
        // Mock data
        LoginDto loginDto = new LoginDto("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        String generatedToken = "generated_token";

        // Mock authenticationManager.authenticate() method
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Mock jwtProvider.generateToken() method
        when(jwtProvider.generateToken(authentication)).thenReturn(generatedToken);

        // Call the method under test
        String result = authService.login(loginDto);

        // Verify the interactions
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider).generateToken(authentication);

        // Assert the result
        Assertions.assertEquals(generatedToken, result);
    }


    @Test
    void testSignup() {
        SignupDto signupDto = new SignupDto("John Doe",
                "john@example.com",
                "VENDOR",
                "password");
        Role userRole = new Role(1, "ROLE_USER");
        Role vendorRole = new Role(2, "ROLE_VENDOR");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(vendorRole);

        Member savedMember = Member.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("encoded_password")
                .roles(roles).build();
        Authentication authentication = mock(Authentication.class);
        String generatedToken = "generated_token";

        // Mock roleRepo.findByName() method
        when(roleRepo.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(roleRepo.findByName("ROLE_VENDOR")).thenReturn(Optional.of(vendorRole));

        // Mock passwordEncoder.encode() method
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encoded_password");

        // Mock memberRepo.save() method
        when(memberRepo.save(any(Member.class))).thenReturn(savedMember);

        // Mock authenticationManager.authenticate() method
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Mock jwtProvider.generateToken() method
        when(jwtProvider.generateToken(authentication)).thenReturn(generatedToken);

        // Call the method under test
        String result = authService.signup(signupDto);

        // Verify the interactions
        verify(roleRepo).findByName("ROLE_USER");
        verify(roleRepo).findByName("ROLE_VENDOR");
        verify(passwordEncoder).encode(signupDto.getPassword());
        verify(memberRepo).save(any(Member.class));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtProvider).generateToken(authentication);

        // Assert the result
        Assertions.assertEquals(generatedToken, result);
    }


}
