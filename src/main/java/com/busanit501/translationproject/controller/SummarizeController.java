package com.busanit501.translationproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}


