package com.book.date.BookingDate.features.rooms.service;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;
    private final MemberRepo memberRepo;

    @Override
    public Room create(CreateRoomDto roomDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepo
                .findByEmailOrName(authentication.getName(), authentication.getName())
                .get();
        return roomDao.save(Room.builder()
                .accessCode(roomDto.getAccessCode())
                .name(roomDto.getName()).owner(member).build());
    }

    @Override
    public List<Room> fetchRooms(String search, String ownerId) {
        return roomDao.fetchRooms(search, ownerId);
    }
}
