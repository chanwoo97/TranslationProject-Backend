package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.TranslateRequest;
import com.busanit501.translationproject.dto.TranslateResponse;
import com.busanit501.translationproject.service.TranslateService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // 1. Spring Security 임포트
import org.springframework.security.core.context.SecurityContextHolder; // 2. Spring Security 임포트
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
@RequiredArgsConstructor // 💡 @Autowired 대신 사용하면 더 좋습니다.
public class TranslateController {

    private final TranslateService translateService;

    // 💡 Lombok의 @RequiredArgsConstructor를 사용하면 이 생성자는 작성할 필요가 없습니다.
    // public TranslateController(TranslateService translateService) {
    //     this.translateService = translateService;
    // }

    @PostMapping
    public ResponseEntity<TranslateResponse> translateText(@RequestBody TranslateRequest request) throws JSONException {
        // 3. 현재 로그인된 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName(); // 사용자의 ID (일반적으로 로그인 시 사용한 username)

        // 4. 서비스 메소드에 memberId를 네 번째 인자로 추가하여 호출
        String translatedText = translateService.translate(request.getText(), request.getSourceLang(), request.getTargetLang(), memberId);

        TranslateResponse response = new TranslateResponse(translatedText);
        return ResponseEntity.ok(response);
    }
}