package com.book.date.BookingDate.controller;

import com.book.date.BookingDate.features.members.controller.AuthController;
import com.book.date.BookingDate.features.members.dto.JwtAuthResponse;
import com.book.date.BookingDate.features.members.dto.LoginDto;
import com.book.date.BookingDate.features.members.dto.SignupDto;
import com.book.date.BookingDate.features.members.service.AuthService;
import com.book.date.BookingDate.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private LoginDto loginDto;
    private SignupDto signupDto;

    private JwtAuthResponse jwtAuthResponse;

    private String prefix = "/api/auth";

    @BeforeEach
    void setUp() {
        loginDto = LoginDto
                .builder().emailOrUsername("emad")
                .password("1234")
                .build();
        signupDto = SignupDto
                .builder()
                .email("tst@tst.com")
                .role("USER")
                .password("1234")
                .build();
        jwtAuthResponse = JwtAuthResponse
                .builder()
                .accessToken("token")
                .tokenType("Bearer")
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_login() throws Exception {
//        give
        when(authService.login(loginDto)).thenReturn(jwtAuthResponse.getAccessToken());

//        when
        ResultActions response = mockMvc.perform(post(prefix + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));
//        then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.accessToken",
                                CoreMatchers.is(jwtAuthResponse.getAccessToken())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.tokenType",
                                CoreMatchers.is(jwtAuthResponse.getTokenType())));

    }

    @Test
    void test_signup() throws Exception {
        // give
        when(authService.signup(signupDto)).thenReturn(jwtAuthResponse.getAccessToken());

        // when
        ResultActions response = mockMvc.perform(post(prefix + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupDto)));
        // then
        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.accessToken",
                                CoreMatchers.is(jwtAuthResponse.getAccessToken())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.tokenType",
                                CoreMatchers.is(jwtAuthResponse.getTokenType())));
    }

    @Test
    void test_bad_credentials() throws Exception {
        //give
        given(authService.login(loginDto))
                .willAnswer(invocation -> {
                    throw new RuntimeException("Invalid credentials");
                });

        // when
        ResultActions response = mockMvc.perform(post(prefix + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Invalid credentials")));
    }

}
