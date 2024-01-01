package com.book.date.BookingDate.features.seats.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignOrRemoveSeatToUserDto {
    private int seatId;
    private String roomName;
    private String accessCode;
    private Integer userId;
}
