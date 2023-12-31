package com.book.date.BookingDate.features.seats.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSeatsDto {
    private String roomName;
    private List<CreateSeatListItem> seats;
}

