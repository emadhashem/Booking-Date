package com.book.date.BookingDate.controller;

import com.book.date.BookingDate.features.seats.controller.SeatController;
import com.book.date.BookingDate.features.seats.controller.dto.AssignOrRemoveSeatToUserDto;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatListItem;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatsDto;
import com.book.date.BookingDate.features.seats.entity.Seat;
import com.book.date.BookingDate.features.seats.service.SeatService;
import com.book.date.BookingDate.filter.JwtAuthenticationFilter;
import com.book.date.BookingDate.provider.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//@WebMvcTest(controllers = SeatController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SeatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeatService seatService;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private UserDetailsService userDetailsService;

    private CreateSeatsDto createSeatsDto;
    private AssignOrRemoveSeatToUserDto assignOrRemoveSeatToUserDto;

    private String prefix;
    private String token;


    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @BeforeEach
    void setUp() {
        createSeatsDto = CreateSeatsDto
                .builder()
                .roomName("room")
                .seats(Arrays.asList(new CreateSeatListItem("title1")))
                .build();
        assignOrRemoveSeatToUserDto = AssignOrRemoveSeatToUserDto
                .builder()
                .seatId(1)
                .userId(1)
                .accessCode("1234")
                .roomName("test")
                .build();
        prefix = "/api/seats";
        token = "Bearer token";
        MockitoAnnotations.openMocks(this);
        when(jwtProvider.validateToken("token")).thenReturn(true);
        when(jwtProvider.getMemberName("token")).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(
                new org.springframework.security.core.userdetails.User(
                        "username", "1234",
                        Set.of(new SimpleGrantedAuthority("ROLE_USER"),
                                new SimpleGrantedAuthority("ROLE_VENDOR"))
                )
        );
    }

    @Test
    void assign_Or_Remove_User() throws Exception {
        //give
        doNothing().when(seatService).assignOrRemoveUser(assignOrRemoveSeatToUserDto);
        //when
        ResultActions result = mockMvc.perform(put(prefix + "/assign")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(assignOrRemoveSeatToUserDto))
        );
        result.andExpect(MockMvcResultMatchers.status().isOk());

        verify(seatService, times(1)).assignOrRemoveUser(assignOrRemoveSeatToUserDto);
    }

    @Test
    void add_Seats_To_Room() throws Exception {
        //give
        doNothing().when(seatService).addSeatsToRoom(createSeatsDto);
        //when
        ResultActions result = mockMvc.perform(post(prefix)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createSeatsDto))
        );
        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        verify(seatService, times(1)).addSeatsToRoom(createSeatsDto);
    }

    @Test
    void fetch_Seats() throws Exception {
        // given
        String roomName = "";
        String search = "";
        List<Seat> seats = Arrays.asList(new Seat(), new Seat());
        when(seatService.fetchSeats(roomName, search)).thenReturn(seats);
        // when
        ResultActions result = mockMvc.perform(get(prefix)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("search", search).param("roomName", roomName)
        );
        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        verify(seatService, times(1)).fetchSeats(roomName, search);
    }

    @Test
    void should_throw_error_without_jwt_assign_Or_Remove_User() throws Exception {
        //give
        doNothing().when(seatService).assignOrRemoveUser(assignOrRemoveSeatToUserDto);
        //when
        ResultActions result = mockMvc
                .perform(put(prefix + "/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignOrRemoveSeatToUserDto))
                );
        result.andExpect(MockMvcResultMatchers.status().isForbidden());

        verify(seatService, times(1)).assignOrRemoveUser(assignOrRemoveSeatToUserDto);
    }
}
