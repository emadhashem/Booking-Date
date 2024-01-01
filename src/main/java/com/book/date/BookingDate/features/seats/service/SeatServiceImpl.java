package com.book.date.BookingDate.features.seats.service;

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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatDao seatDao;
    private final RoomDao roomDao;
    private final SeatRepo seatRepo;
    private final MemberRepo memberRepo;

    @Override
    @Transactional
    public void addSeatsToRoom(CreateSeatsDto createSeatsDto) {
        List<Seat> seatList = new ArrayList<>();
        for (CreateSeatListItem seat : createSeatsDto.getSeats()) {
            seatList.add(new Seat(0, seat.getTitle(), null, null));
        }
        seatDao.addSeatsToRoom(roomDao.findByName(createSeatsDto.getRoomName()),
                seatList
        );
    }

    @Override
    public List<Seat> fetchSeats(String roomName, String search) {
        return seatDao.fetchSeats(roomName, search);
    }

    @Override
    @Transactional
    public void assignOrRemoveUser(AssignOrRemoveSeatToUserDto dto) {
        System.out.println(dto.getUserId());
        Optional<Seat> oSeat = seatRepo.findById(dto.getSeatId());
        if (oSeat.isEmpty()) throw new RuntimeException("Seat not found");
        Room room = roomDao.findByName(dto.getRoomName());
        if (!roomDao.checkAccess(dto.getAccessCode(), room)) {
            throw new RuntimeException("Access Denied");
        }

        Member user = null;
        if (dto.getUserId() != null) user = memberRepo.findById(dto.getUserId()).get();

        if (user != null && isSeatTaken(oSeat.get())) {
            throw new RuntimeException("Seat Already in use.");
        }

        if (user != null && !roomDao.isUserInRoom(user.getId(), room)) {
            throw new RuntimeException("User is not the room" + dto.getRoomName());
        }

        var seat = oSeat.get();
        seat.setUser(user);
        seatRepo.save(seat);
    }

    @Override
    public boolean isSeatTaken(Seat seat) {
        return seat.getUser() != null;
    }
}
