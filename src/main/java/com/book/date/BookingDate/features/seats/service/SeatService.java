package com.book.date.BookingDate.features.seats.service;

import com.book.date.BookingDate.features.seats.controller.dto.AssignOrRemoveSeatToUserDto;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatsDto;
import com.book.date.BookingDate.features.seats.entity.Seat;

import java.util.List;

public interface SeatService {
    void addSeatsToRoom(CreateSeatsDto createSeatsDto);

    List<Seat> fetchSeats(String roomId, String search);

    void assignOrRemoveUser(AssignOrRemoveSeatToUserDto dto);

    boolean isSeatTaken(Seat seat);
}
