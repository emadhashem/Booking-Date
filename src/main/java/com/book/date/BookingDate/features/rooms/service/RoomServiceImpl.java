package com.book.date.BookingDate.features.rooms.service;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.service.MemberService;
import com.book.date.BookingDate.features.rooms.controller.dto.CreateRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.JoinUsersRoomDto;
import com.book.date.BookingDate.features.rooms.controller.dto.RemoveUserDto;
import com.book.date.BookingDate.features.rooms.dao.RoomDao;
import com.book.date.BookingDate.features.rooms.entity.Room;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;
    private final MemberRepo memberRepo;
    private final MemberService memberService;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void addUsersToRoom(JoinUsersRoomDto dto) throws BadRequestException {
        List<Member> users = memberService.findUsersByIds(dto.getUsers());
        Room room = roomDao.findByName(dto.getRoomName());
        if (!checkAccess(dto.getAccessCode(), room)) {
            throw new BadRequestException("Room access denied");
        }
        dto.getUsers().forEach(id -> {
            room.getUsers().forEach(item -> {
                if (item.getId() == id) {
                    throw new RuntimeException("User Already in room");
                }
            });
        });

        users.forEach(item -> {
            item.addRoom(room);
            memberRepo.save(item);
        });
        room.addUsers(users);
        roomDao.save(room);
    }

    @Override
    @Transactional
    public void removeUser(RemoveUserDto dto) throws RuntimeException{
        Room room = roomDao.findByName(dto.getRoomName());

        if (!checkAccess(dto.getAccessCode(), room)) {
            throw new RuntimeException("Room access denied");
        }
        room.setUsers(room.getUsers().stream()
                .filter(item -> item.getId() != dto.getUserId()).collect(Collectors.toList()));
        roomDao.save(room);
    }

    @Override
    public boolean checkAccess(String access, Room room) {
        return roomDao.checkAccess(access, room);
    }
}
