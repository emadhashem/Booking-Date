package com.book.date.BookingDate.users.repository;

import com.book.date.BookingDate.users.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
