package com.book.date.BookingDate.service;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.entity.Role;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.service.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTests {

    @Mock
    private MemberRepo memberRepo;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void find_users_by_ids() {
        List<Integer> userIds = new ArrayList<>(Arrays.asList(1, 2, 3));

        List<Member> members = initMockUsers();
        Mockito.when(memberRepo.findAllById(userIds)).thenReturn(members);

        List<Member> membersResult = memberService.findUsersByIds(userIds);

        Mockito.verify(memberRepo).findAllById(userIds);

        Assertions.assertEquals(members, membersResult);
    }

    private List<Member> initMockUsers() {
        Role userRole = new Role(1, "ROLE_USER");
        Role vendorRole = new Role(2, "ROLE_VENDOR");
        Set<Role> roles = new HashSet<>(), roles1 = new HashSet<>();
        roles.add(userRole);
        roles.add(vendorRole);
        roles1.add(userRole);
        return new ArrayList<>(Arrays.asList(
                Member.builder()
                        .name("emad")
                        .id(1)
                        .email("tst@tst.com")
                        .password("1234")
                        .roles(roles)
                        .build(),
                Member.builder()
                        .name("emad")
                        .id(2)
                        .email("tst@tst.com")
                        .password("1234")
                        .roles(roles1)
                        .build(),
                Member.builder()
                        .name("emad")
                        .id(3)
                        .email("tst@tst.com")
                        .password("1234")
                        .roles(roles1)
                        .build()
        ));
    }

}
