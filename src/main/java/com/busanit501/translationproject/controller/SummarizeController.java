package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.SummarizeRequest;
import com.busanit501.translationproject.dto.SummarizeResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.busanit501.translationproject.service.SummarizeService;

@RestController
@Log4j2
@RequestMapping("/api/summarize")
public class SummarizeController {

    private final SummarizeService summarizeService;

    @Autowired
    public SummarizeController(SummarizeService summarizeService) {
        this.summarizeService = summarizeService;
    }

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

        String result = summarizeService.summarize(request.getText(), language);
        SummarizeResponse response = new SummarizeResponse(result);
        return ResponseEntity.ok(response);
    }
}


