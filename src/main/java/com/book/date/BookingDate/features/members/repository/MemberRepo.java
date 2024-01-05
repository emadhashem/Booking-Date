package com.book.date.BookingDate.features.members.repository;

import com.book.date.BookingDate.features.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {
    Optional<Member> findByEmailOrName(String name, String email);

    List<Member> searchAllByEmailOrName(String email, String name);
}
