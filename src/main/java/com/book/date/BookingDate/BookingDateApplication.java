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
        };
    }
}
