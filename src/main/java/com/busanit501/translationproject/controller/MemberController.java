package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.ChangePasswordRequestDTO;
import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    // 👇 아래 API가 추가되었습니다.
    @Tag(name = "비밀번호 변경", description = "인증된 사용자의 비밀번호를 변경합니다.")
    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDTO requestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName(); // 인증된 사용자의 ID(이름) 가져오기
            log.info("Authenticated memberId for /change-password: " + memberId);

            memberService.changePassword(memberId, requestDTO);

            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            // 서비스 로직에서 던진 예외 (비밀번호 불일치 등) 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외 서버 내부 오류 처리
            log.error("Password change error: ", e);
            return ResponseEntity.internalServerError().body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }
}
