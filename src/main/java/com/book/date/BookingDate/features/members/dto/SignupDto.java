package com.book.date.BookingDate.features.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private String email;
    private String name;
    private String role;
    private String password;
}
