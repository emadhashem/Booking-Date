package com.book.date.BookingDate.features.seats.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignOrRemoveSeatToUserDto {
    private int seatId;
    private String roomName;
    private String accessCode;
    private Integer userId;
}
