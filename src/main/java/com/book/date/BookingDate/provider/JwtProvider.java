package com.book.date.BookingDate.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.security.Key;
import java.util.Date;


@Component
public class JwtProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${server.jwt-secret}")
    private String jwtSecret;

    @Value("${server.jwt-expire-milli}")
    private Long jwtExpire;

    public String generateToken(Authentication authentication) {
        var name = authentication.getName();
        Date expireDate = new Date(System.currentTimeMillis() + jwtExpire);
        return Jwts
                .builder()
                .setSubject(name)
                .setExpiration(expireDate)
                .signWith(getKey())
                .compact();
    }


    private Key getKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getMemberName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid jwt token : {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            LOGGER.error(" jwt token expired : {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.error(" jwt token is UnSupported : {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error(" jwt token claims is empty : {}", ex.getMessage());
        }
        return false;
    }
}
