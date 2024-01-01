package com.book.date.BookingDate.features.rooms.service;

import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.JoinUsersRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.RemoveUserDto;
import com.book.date.BookingDate.features.rooms.entity.Room;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface RoomService {

    Room create(CreateRoomDto roomDto);

    List<Room> fetchRooms(String search, String ownerId);

    void addUsersToRoom(JoinUsersRoomDto dto) throws BadRequestException;

    void removeUser(RemoveUserDto dto) throws RuntimeException;

    boolean checkAccess(String access, Room room);
}
