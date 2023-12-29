package com.book.date.BookingDate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    @GetMapping("/test")
    public ResponseEntity<String> testGet() {
        return ResponseEntity.ok("Get works");
    }

    @PostMapping("/test")
    public ResponseEntity<String> testPost() {
        return ResponseEntity.ok("Post works");
    }
}
