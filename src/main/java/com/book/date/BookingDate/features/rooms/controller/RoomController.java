package com.book.date.BookingDate.features.rooms.controller;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.book.date.BookingDate.features.rooms.service.RoomService;
import com.book.date.BookingDate.features.seats.dao.SeatDao;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<Room> create(@RequestBody CreateRoomDto roomDto) {

        return ResponseEntity
                .ok()
                .body(roomService.create(roomDto));
    }

    @GetMapping
    public ResponseEntity<List<Room>>
    fetchRooms(@RequestParam(name = "search", required = false) String search,
               @RequestParam(name = "ownerId", required = false) String ownerId) {
        return ResponseEntity.ok().body(roomService.fetchRooms(search, ownerId));
    }
}

