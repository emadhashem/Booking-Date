package com.book.date.BookingDate.features.seats.controller;

import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.seats.controller.dto.AssignOrRemoveSeatToUserDto;
import com.book.date.BookingDate.features.seats.dao.SeatDao;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatDto;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatListItem;
import com.book.date.BookingDate.features.seats.controller.dto.CreateSeatsDto;
import com.book.date.BookingDate.features.seats.entity.Seat;
import com.book.date.BookingDate.features.seats.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<String> addSeatsToRoom(@RequestBody CreateSeatsDto createSeatsDto) {
        seatService.addSeatsToRoom(createSeatsDto);
        return ResponseEntity.ok().body("Seats added to " + createSeatsDto.getRoomName());
    }

    @GetMapping
    public ResponseEntity<List<Seat>> fetchSeats(
            @RequestParam(name = "roomName", required = false, defaultValue = "") String roomName,
            @RequestParam(name = "search", required = false, defaultValue = "") String search

    ) {
        return ResponseEntity.ok().body(seatService.fetchSeats(roomName, search));
    }

    @PutMapping("/assign")
    public ResponseEntity<String> assignOrRemoveUser(@RequestBody AssignOrRemoveSeatToUserDto dto) {
        seatService.assignOrRemoveUser(dto);
        return ResponseEntity.ok().body("Seat updated.");
    }
}
