package com.book.date.BookingDate.features.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

}
