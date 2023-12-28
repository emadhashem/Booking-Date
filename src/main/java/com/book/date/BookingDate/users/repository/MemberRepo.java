package com.book.date.BookingDate.users.repository;

import com.book.date.BookingDate.users.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Integer> {

    Member findByEmail(String email);
}
