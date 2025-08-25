package com.busanit501.translationproject.controller;

import com.busanit501.translationproject.dto.TranslateRequest;
import com.busanit501.translationproject.dto.TranslateResponse;
import com.busanit501.translationproject.service.TranslateService;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslateController {

    private final TranslateService translateService;

    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @PostMapping
    public ResponseEntity<TranslateResponse> translateText(@RequestBody TranslateRequest request) throws JSONException {
        String translatedText = translateService.translate(request.getText(), request.getSourceLang(), request.getTargetLang());
        TranslateResponse response = new TranslateResponse(translatedText);
        return ResponseEntity.ok(response);
    }
}
