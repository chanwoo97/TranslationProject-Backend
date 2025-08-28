package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.ParaphraseRequest;
import com.busanit501.translationproject.dto.ParaphraseResponse;
import com.busanit501.translationproject.service.ParaphraseService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/paraphrase")
public class ParaphraseController {

    private final ParaphraseService paraphraseService;

    public ParaphraseController(ParaphraseService paraphraseService) {
        this.paraphraseService = paraphraseService;
    }

    // GET 요청: URL 쿼리 파라미터로 데이터 조회
    @GetMapping
    public String paraphraseGet(@RequestParam String text, @RequestParam(required = false) String targetLang) throws JSONException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String language = (targetLang != null && !targetLang.isEmpty()) ? targetLang : "ko";
        return paraphraseService.paraphrase(text, language, memberId);
    }

    // POST 요청: 요청 본문(body)에 데이터 생성
    @PostMapping
    public ResponseEntity<ParaphraseResponse> paraphrasePost(@RequestBody ParaphraseRequest request) throws JSONException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = authentication.getName();
        String text = request.getText();
        String language = (request.getLanguage() != null && !request.getLanguage().isEmpty()) ? request.getLanguage() : "ko";

        log.info("ParaphraseController - 프론트에서 보낸 text : " + text);
        log.info("ParaphraseController - 프론트에서 보낸 language : " + language);
        String paraphrasedText = paraphraseService.paraphrase(text, language, memberId);

        ParaphraseResponse response = new ParaphraseResponse(paraphrasedText);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public String paraphraseDelete(@PathVariable("id") Long id) {
        // TODO: id에 해당하는 데이터를 데이터베이스에서 삭제하는 로직을 추가할 수 있습니다.
        return "ID " + id + "에 해당하는 패러프레이징 기록을 삭제했습니다.";
    }
}