package com.book.date.BookingDate.users.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login() {
        return "Login";
    }

    @PostMapping("/signup")
    public String signup() {
        return "signup";
    }
}
