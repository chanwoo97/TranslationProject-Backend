package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class RegisterController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> join(@Valid @RequestBody MemberDTO memberDTO) {
        log.info("회원가입 요청 수신 : " + memberDTO);
        try {
            memberService.join(memberDTO);
            return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
        }  catch (IllegalArgumentException e) {
            log.info("회원가입 실패 (비즈니스 로직) : " + e.getMessage());  // MemberServiceImpl에서 던지는 예외를 직접 처리
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
        } catch (Exception e) {
            log.info("회원가입 실패 : " + e.getMessage());
            return new ResponseEntity<>("회원가입에 실패하였습니다. : " + e.getMessage(),
                    HttpStatus.BAD_REQUEST );
        }
    }
}
