package com.book.date.BookingDate.features.members.service;

import com.book.date.BookingDate.features.members.entity.Member;

import java.util.List;

public interface MemberService {

    List<Member> findUsersByIds(List<Integer> userIds);

    List<Member> searchUsers(String search);
}
