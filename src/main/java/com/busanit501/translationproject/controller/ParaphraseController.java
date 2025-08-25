package com.busanit501.translationproject.controller;

import com.busanit501.react_project.dto.ParaphraseRequest;
import com.busanit501.react_project.service.ParaphraseService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paraphrase")
public class ParaphraseController {

    private final ParaphraseService paraphraseService;

    public ParaphraseController(ParaphraseService paraphraseService) {
        this.paraphraseService = paraphraseService;
    }

    // GET 요청: URL 쿼리 파라미터로 데이터 조회
    @GetMapping
    public String paraphraseGet(@RequestParam String text, @RequestParam(required = false) String targetLang) throws JSONException {
        String language = (targetLang != null && !targetLang.isEmpty()) ? targetLang : "ko";
        return paraphraseService.paraphrase(text, language);
    }

    // POST 요청: 요청 본문(body)에 데이터 생성
    @PostMapping
    public String paraphrasePost(@RequestBody ParaphraseRequest request) throws JSONException {
        String text = request.getText();
        String language = (request.getLanguage() != null && !request.getLanguage().isEmpty()) ? request.getLanguage() : "ko";

        // TODO: 여기에 데이터베이스에 요청 기록을 저장하는 로직을 추가할 수 있습니다.

        return paraphraseService.paraphrase(text, language);
    }

    // DELETE 요청: URL 경로의 ID를 사용하여 특정 데이터 삭제
    @DeleteMapping("/{id}")
    public String paraphraseDelete(@PathVariable("id") Long id) {
        // TODO: id에 해당하는 데이터를 데이터베이스에서 삭제하는 로직을 추가할 수 있습니다.
        return "ID " + id + "에 해당하는 패러프레이징 기록을 삭제했습니다.";
    }
}