package com.book.date.BookingDate.features.members.controller;

import com.book.date.BookingDate.features.members.entity.Member;
import com.book.date.BookingDate.features.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    ResponseEntity<List<Member>> searchUsers(
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        return ResponseEntity.ok().body(memberService.searchUsers(search));
    }
}
