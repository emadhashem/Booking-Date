package com.book.date.BookingDate.features.seats.repository;

import com.book.date.BookingDate.features.seats.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer> {
}
