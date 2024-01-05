package com.book.date.BookingDate.service;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.service.MemberService;
import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.JoinUsersRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.RemoveUserDto;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.book.date.BookingDate.features.rooms.service.RoomServiceImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    @Mock
    private RoomDao roomDao;
    @Mock
    private MemberRepo memberRepo;
    @Mock
    private MemberService memberService;

    @Mock
    private Authentication authentication;


    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void check_access_function() {
        String accessArg = "123456";
        Room room = Room.builder()
                .name("room1")
                .accessCode("123456")
                .build();

        when(roomDao.checkAccess(accessArg, room)).thenReturn(true);

        var result = roomService.checkAccess(accessArg, room);
        Assertions.assertTrue(result);
    }

    @Test
    void remove_user() {

        RemoveUserDto dto = RemoveUserDto
                .builder()
                .userId(1)
                .roomName("room")
                .accessCode("1234")
                .build();
        Member member = Member
                .builder()
                .name("emad")
                .email("tst@tst.com")
                .id(1)
                .build();
        Room room = Room.builder()
                .name("room1")
                .accessCode("123456")
                .users(new ArrayList<>(Arrays.asList(member)))
                .build();

        when(roomDao.findByName(dto.getRoomName())).thenReturn(room);
        when(roomService.checkAccess(dto.getAccessCode(), room)).thenReturn(true);
        Assertions.assertAll(() -> {
            roomService.removeUser(dto);
        });

    }

    @Test
    void remove_user_with_wrong_access_code() {

        RemoveUserDto dto = RemoveUserDto
                .builder()
                .userId(1)
                .roomName("room")
                .accessCode("1234")
                .build();
        Member member = Member
                .builder()
                .name("emad")
                .email("tst@tst.com")
                .id(1)
                .build();
        Room room = Room.builder()
                .name("room1")
                .accessCode("123456")
                .users(new ArrayList<>(Arrays.asList(member)))
                .build();

        when(roomDao.findByName(dto.getRoomName())).thenReturn(room);
        when(roomService.checkAccess(dto.getAccessCode(), room)).thenReturn(false);
        Assertions.assertThrows(RuntimeException.class, () -> {
            roomService.removeUser(dto);
        });

    }

    @Test
    void fetch_rooms() {
        String search = "room";
        String ownerId = "1";

        List<Room> rooms = new ArrayList<>(
                Arrays.asList(
                        Room.builder()
                                .name("room1")
                                .build(),
                        Room.builder()
                                .name("2")
                                .build()
                )
        );
        when(roomDao.fetchRooms(search, ownerId)).thenReturn(rooms);

        var results = roomDao.fetchRooms(search, ownerId);
        Assertions.assertEquals(rooms, results);
    }

    @Test
    void create_room() {
        SecurityContextHolder.setContext(mock(SecurityContext.class));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        Member member = new Member();
        when(memberRepo.findByEmailOrName(anyString(), anyString())).thenReturn(Optional.of(member));
        when(authentication.getName()).thenReturn("emad");
        CreateRoomDto roomDto = new CreateRoomDto();
        roomDto.setAccessCode("1234");
        roomDto.setName("Test Room");


        Room room = Room
                .builder()
                .name(roomDto.getName())
                .accessCode(roomDto.getAccessCode())
                .owner(member)
                .build();
        when(roomDao.save(any(Room.class))).thenReturn(room);


        Room createdRoom = roomService.create(roomDto);

        verify(authentication, times(2)).getName();
        verify(memberRepo).findByEmailOrName(authentication.getName(), authentication.getName());
        verify(roomDao).save(room);

        Assertions.assertEquals(room, createdRoom);
    }

    @Test
    void add_Users_To_Room() throws BadRequestException {
        JoinUsersRoomDto request = new JoinUsersRoomDto();
        request.setUsers(Arrays.asList(1, 2));
        request.setRoomName("Room 1");
        request.setAccessCode("1234");

        // Mock returned objects
        List<Member> users = Arrays.asList(new Member(), new Member());
        Room room = new Room();
        room.addUsers(users);

        // Mock service calls
        when(memberService.findUsersByIds(request.getUsers())).thenReturn(users);
        when(roomDao.findByName(request.getRoomName())).thenReturn(room);
        when(roomDao.checkAccess(request.getAccessCode(), room)).thenReturn(true);

        // Call the method
        roomService.addUsersToRoom(request);

        // Verify interactions
        verify(memberService).findUsersByIds(request.getUsers());
        verify(roomDao).findByName(request.getRoomName());
        verify(roomDao).checkAccess(request.getAccessCode(), room);
        verify(memberRepo, times(2)).save(any());
        verify(roomDao).save(room);
    }
}
