package com.book.date.BookingDate.features.seats.entity;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.rooms.entity.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    @JoinColumn(name = "room_name")
    @JsonBackReference
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Member user;
}
