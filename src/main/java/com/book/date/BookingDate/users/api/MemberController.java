package com.book.date.BookingDate.users.api;

import com.book.date.BookingDate.users.entity.Member;
import com.book.date.BookingDate.users.entity.Role;
import com.book.date.BookingDate.users.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers() {
        return ResponseEntity.ok().body(memberService.getMembers());
    }

    @PostMapping("/members")
    public ResponseEntity<Member> saveNewMember(@RequestBody Member member) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/members").toString());
        return ResponseEntity.created(uri).body(memberService.save(member));
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> saveNewRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/roles").toString());
        return ResponseEntity.created(uri).body(memberService.saveRole(role));
    }

    @PostMapping("/members/role")
    public ResponseEntity<?> addRoleToMember(@RequestBody RoleToMemberForm form) {
        memberService.addRoleToMember(form.getMemberEmail(), form.getRoleName());
        return ResponseEntity.ok().build();

    }


}

@Data
class RoleToMemberForm {
    private String memberEmail;
    private String roleName;
}