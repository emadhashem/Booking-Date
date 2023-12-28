package com.book.date.BookingDate.users.service;

import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;

import java.util.List;

public interface MemberService {

    Member save(Member member);

    Role saveRole(Role role);

    void addRoleToMember(String email, String roleName);

    Member getMemberByEmail(String email);

    List<Member> getMembers();
}
