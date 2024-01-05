package com.book.date.BookingDate.features.members.service;

import com.book.date.BookingDate.features.members.dto.LoginDto;
import com.book.date.BookingDate.features.members.dto.SignupDto;
import org.apache.coyote.BadRequestException;

public interface AuthService {
    String login(LoginDto loginDto);
    String signup(SignupDto signupDto);
}
