package com.book.date.BookingDate.features.seats.service;

import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatListItem;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatsDto;
import com.book.date.BookingDate.features.seats.dao.SeatDao;
import com.book.date.BookingDate.features.seats.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatDao seatDao;
    private final RoomDao roomDao;

    @Override
    public void addSeatsToRoom(CreateSeatsDto createSeatsDto) {
        List<Seat> seatList = new ArrayList<>();
        for (CreateSeatListItem seat : createSeatsDto.getSeats()) {
            seatList.add(new Seat(0, seat.getTitle(), null));
        }
        seatDao.addSeatsToRoom(roomDao.findByName(createSeatsDto.getRoomName()),
                seatList
        );
    }

    @Override
    public List<Seat> fetchSeats(String roomName, String search) {
        return seatDao.fetchSeats(roomName, search);
    }
}
