package com.busanit501.translationproject.service;

import org.springframework.stereotype.Service;

@Service
public class SummarizeService {

    public String summarize(String text, String language) {
        // TODO: OpenAI 연동 예정. 현재는 간단한 자리표시자 반환
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        return "[" + language + "] " + text.substring(0, Math.min(text.length(), 80)) + (text.length() > 80 ? "..." : "");
    }
}


