package com.book.date.BookingDate.service;


import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.book.date.BookingDate.features.seats.controller.dto.AssignOrRemoveSeatToUserDto;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatListItem;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatsDto;
import com.book.date.BookingDate.features.seats.dao.SeatDao;
import com.book.date.BookingDate.features.seats.entity.Seat;
import com.book.date.BookingDate.features.seats.repository.SeatRepo;
import com.book.date.BookingDate.features.seats.service.SeatServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class SeatServiceTests {
    @Mock
    private SeatDao seatDao;
    @Mock
    private RoomDao roomDao;
    @Mock
    private SeatRepo seatRepo;
    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private SeatServiceImpl seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetch_Seats() {
        String roomName = "test";
        String search = "test";
        List<Seat> seatList = Arrays.asList(new Seat(), new Seat());
        when(seatDao.fetchSeats(roomName, search)).thenReturn(seatList);

        var result = seatService.fetchSeats(roomName, search);
        Assertions.assertEquals(seatList, result);

    }

    @Test
    void add_Seats_To_Room() {
        CreateSeatsDto request = new CreateSeatsDto();
        request.setRoomName("Room 1");

        CreateSeatListItem seat1 = new CreateSeatListItem("Seat 1");
        CreateSeatListItem seat2 = new CreateSeatListItem("Seat 2");
        request.setSeats(Arrays.asList(seat1, seat2));

        Room room = new Room();
        when(roomDao.findByName(request.getRoomName())).thenReturn(room);
        seatService.addSeatsToRoom(request);
        List<Seat> expectedSeats = Arrays.asList(
                new Seat(0, "Seat 1", null, null),
                new Seat(0, "Seat 2", null, null)
        );
        room.setSeats(expectedSeats);

        verify(seatDao).addSeatsToRoom(room, expectedSeats);

    }

    @Test
    void should_user_assign_to_seat() {

        // give-arrange
        AssignOrRemoveSeatToUserDto reqDto = AssignOrRemoveSeatToUserDto
                .builder()
                .userId(1)
                .seatId(23)
                .roomName("room")
                .accessCode("1234")
                .build();

        Seat seat = Seat
                .builder()
                .title("test")
                .id(23)
                .build();

        Room room = Room
                .builder()
                .name("room")
                .accessCode("1234")
                .build();

        Member member = Member
                .builder()
                .email("tst@tst.com")
                .name("test")
                .id(1)
                .password("1234")
                .build();

        when(seatRepo.findById(reqDto.getSeatId())).thenReturn(Optional.of(seat));

        when(roomDao.findByName(reqDto.getRoomName())).thenReturn(room);

        when(memberRepo.findById(reqDto.getUserId())).thenReturn(Optional.of(member));

        when(roomDao.checkAccess(reqDto.getAccessCode(), room)).thenReturn(true);

        when(roomDao.isUserInRoom(reqDto.getUserId(), room)).thenReturn(true);

        // when-act
        seatService.assignOrRemoveUser(reqDto);
        // then-assert

        Assertions.assertEquals(1, seat.getUser().getId());
        verify(seatRepo, times(1)).findById(reqDto.getSeatId());
        verify(roomDao, times(1)).findByName(reqDto.getRoomName());
        verify(memberRepo, times(1)).findById(reqDto.getUserId());
        verify(roomDao, times(1)).isUserInRoom(reqDto.getUserId(), room);
        verify(roomDao, times(1)).checkAccess(reqDto.getAccessCode(), room);
    }
}
