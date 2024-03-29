package com.book.date.BookingDate.features.rooms.entity;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.seats.entity.Seat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "access_code")
    private String accessCode;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Member owner;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats;

    @ManyToMany
    @JoinTable(name = "room_users"
            , joinColumns = @JoinColumn(name = "room_name")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Member> users;

    public void addSeat(Seat seat) {
        if (seats == null) {
            seats = new ArrayList<>();
        }
        seats.add(seat);
        seat.setRoom(this);
    }

    public void addUsers(List<Member> _users) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.addAll(_users);
    }

}
