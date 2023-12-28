package com.book.date.BookingDate.users.service;

import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;
import com.book.date.BookingDate.users.repository.MemberRepo;
import com.book.date.BookingDate.users.repository.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepo.findByEmail(username);
        if (member == null) {
            log.error("User {} Not found", username);
            throw new UsernameNotFoundException("User Not found");
        } else {
            log.info("User {} found", username);
        }
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Role role : member.getRoles()) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework
                .security.core.userdetails
                .User(member.getEmail(), member.getPassword(), simpleGrantedAuthorities);
    }

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
