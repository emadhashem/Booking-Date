package com.book.date.BookingDate;

import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;
import com.book.date.BookingDate.users.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookingDateApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingDateApplication.class, args);
    }

    @Bean
    CommandLineRunner run(MemberService memberService) {
        return args -> {
            memberService.saveRole(new Role("ROLE_USER"));
            memberService.saveRole(new Role("ROLE_VENDOR"));

            memberService.save(new Member("tst1@tst.com", "test1", "1234"));
            memberService.save(new Member("tst2@tst.com", "test2", "1234"));
            memberService.save(new Member("tst3@tst.com", "test3", "1234"));

            memberService.addRoleToMember("tst1@tst.com", "ROLE_VENDOR");
            memberService.addRoleToMember("tst1@tst.com", "ROLE_USER");
            memberService.addRoleToMember("tst2@tst.com", "ROLE_USER");
            memberService.addRoleToMember("tst3@tst.com", "ROLE_USER");
        };
    }
}
