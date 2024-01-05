package com.book.date.BookingDate.features.rooms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveUserDto {
    private String roomName;
    private int userId;
    private String accessCode;
}
