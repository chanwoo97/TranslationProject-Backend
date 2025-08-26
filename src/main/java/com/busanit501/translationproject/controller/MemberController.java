package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.service.MemberService;
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
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) {
        log.info("회원가입 요청 수신 : " + memberDTO);
        try {
            memberService.join(memberDTO);
            return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            log.error("회원가입 실패 : " + e.getMessage());
            return new ResponseEntity<>("회원가입에 실패하였습니다. : " + e.getMessage(),
                    HttpStatus.BAD_REQUEST );
        }
    }
}
