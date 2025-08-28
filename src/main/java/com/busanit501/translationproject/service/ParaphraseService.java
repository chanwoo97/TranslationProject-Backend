package com.busanit501.translationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
@RequiredArgsConstructor // ★ final 필드 생성자 주입
public class ParaphraseService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    // ★ HistoryService 주입
    private final HistoryService historyService;

    // ★ memberId를 파라미터로 추가
    public String paraphrase(String text, String language, String memberId) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String langInstruction = "";
        // ... (기존 switch-case 문은 변경 없음)
        switch (language) {
            case "ko":
                langInstruction = "한국어로 패러프레이징 해줘. 반드시 한국어로만 답변해야 합니다.";
                break;
            case "en":
                langInstruction = "Paraphrase this in English. You must respond only in English.";
                break;
            // ... (기타 언어 케이스 생략) ...
            default:
                langInstruction = "한국어로 패러프레이징 해줘. 반드시 한국어로만 답변해야 합니다.";
                break;
        }

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "당신은 모든 언어를 지원하는 패러프레이징 전문가입니다.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "다음 문장을 의미를 유지하면서 " + langInstruction + ": \"" + text + "\"");

        JSONArray messages = new JSONArray();
        messages.put(systemMessage);
        messages.put(userMessage);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(response.getBody());
                String paraphrasedText = responseJson
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();

                // ★★★★★ 의역 성공 시, 히스토리 저장 ★★★★★
                historyService.saveHistory(memberId, "의역", text, paraphrasedText);
                log.info(memberId + " 사용자가 의역 기능을 사용했습니다.");

                return paraphrasedText;
            } else {
                log.error("OpenAI API Error: " + response.getStatusCode() + " | Body: " + response.getBody());
                return "Error: OpenAI API returned status code " + response.getStatusCode();
            }
        } catch (Exception e) {
            log.error("API Call Exception: ", e);
            return "Error during API call: " + e.getMessage();
        }
    }
}