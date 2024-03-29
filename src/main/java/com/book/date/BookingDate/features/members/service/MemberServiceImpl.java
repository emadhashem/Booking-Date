package com.book.date.BookingDate.features.members.service;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepo memberRepo;

    @Override
    public List<Member> findUsersByIds(List<Integer> userIds) {
        return memberRepo.findAllById(userIds);
    }

    @Override
    public List<Member> searchUsers(String search) {
        return memberRepo.searchAllByEmailOrName(search, search);
    }
}
