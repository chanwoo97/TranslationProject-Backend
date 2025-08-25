package com.busanit501.translationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateRequest {
    private String text;
    private String sourceLang;
    private String targetLang;
}