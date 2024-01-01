package com.book.date.BookingDate.features.seats.repository;

import com.book.date.BookingDate.features.seats.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepo extends JpaRepository<Seat, Integer> {
}
