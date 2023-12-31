package com.book.date.BookingDate.features.seats.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSeatDto {
    private String roomName;
    private String title;
}
