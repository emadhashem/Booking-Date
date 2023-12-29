package com.book.date.BookingDate.features.members.repository;

import com.book.date.BookingDate.features.members.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
