package com.book.date.BookingDate.repository;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.entity.Role;
import com.book.date.BookingDate.features.members.repository.MemberRepo;
import com.book.date.BookingDate.features.members.repository.RoleRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MemberRepositoryTests {

    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private RoleRepo roleRepo;
    private Set<Role> roles;

    @BeforeEach
    void setUp() {
        roles = new HashSet<>();
        roles.add(new Role(0, "ROLE_USER"));
        roles.add(new Role(0, "ROLE_VENDOR"));
    }

    @Test
    public void is_repo_initialized() {
        Assertions.assertNotNull(memberRepo);
    }

    @Test
    public void create_new_user() {
        Member member = Member.builder().name("1emad")
                .roles(roles)
                .password("1234")
                .email("tst1@tst.com")
                .build();
        var newMember = memberRepo.save(member);
        Assertions.assertNotNull(newMember);
        Assertions.assertEquals("1emad", newMember.getName());
        Assertions.assertEquals("1234", newMember.getPassword());
        Assertions.assertEquals("tst1@tst.com", newMember.getEmail());
        Assertions.assertArrayEquals(new List[]{roles.stream().toList()},
                new List[]{newMember.getRoles().stream().toList()});
        memberRepo.delete(newMember);
    }

    @Test
    void create_2_users_with_same_name_and_should_thrown_error() {
        Member member = Member.builder().name("emad")
                .roles(roles)
                .password("1234")
                .email("tst@tst.com")
                .build();
        Member member2 = Member.builder().name("emad")
                .roles(roles)
                .password("1234")
                .email("tst@tst.com")
                .build();
        memberRepo.save(member);
        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            memberRepo.save(member2);
        });

        String expectedMessage = "violation";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void find_member_by_id() {
        Member member = Member.builder().name("emad")
                .roles(roles)
                .password("1234")
                .email("tst@tst.com")
                .build();
        Member member2 = memberRepo.save(member);
        Member member1 = memberRepo.findById(member2.getId()).get();
        Assertions.assertNotNull(member2);
        Assertions.assertEquals("emad", member1.getName());
    }

    @Test
    void find_member_by_email_or_name() {
        Member member = Member.builder().name("emad")
                .roles(roles)
                .password("1234")
                .email("tst@tst.com")
                .build();
        Member member2 = memberRepo.save(member);
        Member member1 = memberRepo.findByEmailOrName(member2.getEmail(), member2.getName()).get();
        Assertions.assertNotNull(member2);
        Assertions.assertEquals("emad", member1.getName());
    }

}
