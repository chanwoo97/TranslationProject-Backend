package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.MemberDTO;
import com.busanit501.translationproject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService memberService;


    // 아이디 중복 확인
    @GetMapping("/checkId")
    public ResponseEntity<String> checkId(@RequestParam("memberId") String memberId) {
        log.info("아이디 중복 확인 요청 : " + memberId);
        boolean check = memberService.checkId(memberId);
        if (check) {
            return new ResponseEntity<>("이미 사용 중인 아이디입니다.",
                    HttpStatus.CONFLICT); // 409 Conflict
        } else {
            return new ResponseEntity<>("사용 가능한 아이디입니다.",
                    HttpStatus.OK); // 200 ok
        }
    }
}
