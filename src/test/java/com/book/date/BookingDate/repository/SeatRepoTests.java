package com.book.date.BookingDate.repository;

import com.book.date.BookingDate.features.seats.entity.Seat;
import com.book.date.BookingDate.features.seats.repository.SeatRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SeatRepoTests {

    @Autowired
    private SeatRepo seatRepo;

    @Test
    void seat_repo_init() {
        Assertions.assertNotNull(seatRepo);
    }

    @Test
    void create_new_seat() {

        Seat seat = Seat.builder()
                .title("title")
                .build();
        Assertions.assertEquals("title", seat.getTitle());
        Assertions.assertNull(seat.getUser());
    }
}
