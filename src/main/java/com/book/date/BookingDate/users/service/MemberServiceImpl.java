package com.book.date.BookingDate.users.service;

import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;
import com.book.date.BookingDate.users.repository.MemberRepo;
import com.book.date.BookingDate.users.repository.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;
    private final RoleRepo roleRepo;

    @Override
    public Member save(Member member) {
        log.info("Saving new user {}", member.getEmail());
        return memberRepo.save(member);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());

        return roleRepo.save(role);
    }

    @Override
    public void addRoleToMember(String email, String roleName) {
        log.info("Adding role {} to member {}", roleName, email);

        Member member = memberRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        member.getRoles().add(role);
    }

    @Override
    public Member getMemberByEmail(String email) {
        log.info("Fetch member {}", email);

        return memberRepo.findByEmail(email);
    }

    @Override
    public List<Member> getMembers() {
        log.info("Fetch members");
        return memberRepo.findAll();
    }
}
