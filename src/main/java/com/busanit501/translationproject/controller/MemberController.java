package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Tag(name = "멤버 토큰 테스트", description = "멤버 토큰 활성화 테스트")
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public MemberDTO me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName(); // Get the username (memberId) from the authenticated principal
        log.info("Authenticated memberId: " + memberId);
        return memberService.getMember(memberId);
    }
}
