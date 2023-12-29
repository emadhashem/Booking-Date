package com.book.date.BookingDate.features.members.repository;

import com.book.date.BookingDate.features.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Integer> {
    Optional<Member> findByName(String name);

    Boolean existsByEmail(String email);

    Optional<Member> findByEmailOrName(String name, String email);

    Boolean existsByName(String name);
}
