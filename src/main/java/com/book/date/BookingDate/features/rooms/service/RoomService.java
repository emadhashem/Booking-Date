package com.book.date.BookingDate.features.rooms.service;

import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.entity.Room;

import java.util.List;

public interface RoomService {

    Room create(CreateRoomDto roomDto);

    List<Room> fetchRooms(String search, String ownerId);
}
