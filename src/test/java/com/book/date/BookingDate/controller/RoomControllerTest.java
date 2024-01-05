package com.book.date.BookingDate.controller;

import com.book.date.BookingDate.features.rooms.controller.RoomController;
import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.JoinUsersRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.RemoveUserDto;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.book.date.BookingDate.features.rooms.service.RoomService;
import com.book.date.BookingDate.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomService roomService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private String prefix;
    private CreateRoomDto createRoomDto;
    private JoinUsersRoomDto joinUsersRoomDto;
    private RemoveUserDto removeUserDto;

    @BeforeEach
    void setUp() {

        prefix = "/api/rooms";
        createRoomDto = CreateRoomDto
                .builder()
                .name("room")
                .accessCode("test")
                .build();
        joinUsersRoomDto = JoinUsersRoomDto
                .builder()
                .users(Arrays.asList(1, 2))
                .roomName("room")
                .accessCode("test")
                .build();
        removeUserDto = RemoveUserDto
                .builder()
                .accessCode("test")
                .roomName("room")
                .userId(1)
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_room() throws Exception {
        // given
        given(roomService.create(createRoomDto)).willReturn(new Room());
        // when
        mockMvc.perform(post("/api/rooms/create")
                        .header("Authorization", "Bearer " + "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRoomDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(roomService, times(1)).create(createRoomDto);
    }

    @Test
    void fetch_Rooms() throws Exception {
        // given
        String search = "";
        String ownerId = "";
        given(roomService.fetchRooms(search, ownerId)).willReturn(Arrays.asList(new Room(), new Room()));

        // when
        ResultActions result = mockMvc.perform(get(prefix)
                .param("search", search)
                .param("ownerId", ownerId)
                .header("Authorization", "Bearer " + "token")
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void add_Users_To_Room() throws Exception {
        // given
        doNothing().when(roomService).addUsersToRoom(joinUsersRoomDto);

        // when
        ResultActions result = mockMvc.perform(post(prefix + "/add-users")
                .header("Authorization", "Bearer " + "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinUsersRoomDto))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        verify(roomService, times(1)).addUsersToRoom(joinUsersRoomDto);
    }

    @Test
    void delete_user() throws Exception {
        // given
        doNothing().when(roomService).removeUser(removeUserDto);
        // when
        ResultActions result = mockMvc.perform(delete(prefix + "/delete-user")
                .header("Authorization", "Bearer " + "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(removeUserDto))
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
        verify(roomService, times(1)).removeUser(removeUserDto);


    }

}
