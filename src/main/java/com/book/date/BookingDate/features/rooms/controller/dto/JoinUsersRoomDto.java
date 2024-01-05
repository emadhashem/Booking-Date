package com.book.date.BookingDate.features.rooms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinUsersRoomDto {
    private String roomName;
    private List<Integer> users;
    private String accessCode;

}


