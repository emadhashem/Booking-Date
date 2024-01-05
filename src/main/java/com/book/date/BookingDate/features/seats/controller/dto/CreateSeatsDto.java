package com.book.date.BookingDate.features.seats.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSeatsDto {
    private String roomName;
    private List<CreateSeatListItem> seats;
}

