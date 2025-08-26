package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.SummarizeRequest;
import com.busanit501.translationproject.dto.SummarizeResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/api/summarize")
public class SummarizeController {

    @GetMapping
    public ResponseEntity<String> health() {
        log.info("SummarizeController health check");
        return ResponseEntity.ok("summarize-ready");
    }

    @PostMapping
    public ResponseEntity<SummarizeResponse> summarize(@RequestBody SummarizeRequest request) {
        log.info("SummarizeController - text: {}", request.getText());
        String language = (request.getLanguage() != null && !request.getLanguage().isEmpty()) ? request.getLanguage() : "ko";
        log.info("SummarizeController - language: {}", language);

        // TODO: SummarizeService 연동 예정. 지금은 자리표시자 응답 반환
        SummarizeResponse response = new SummarizeResponse("TBD: summarize result");
        return ResponseEntity.ok(response);
    }
}


