package com.busanit501.translationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어줍니다.
public class TranslateService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final String GOOGLE_TRANSLATE_URL = "https://translation.googleapis.com/language/translate/v2";

    // HistoryService를 주입받습니다.
    private final HistoryService historyService;

    public String translate(String text, String sourceLang, String targetLang, String memberId) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_TRANSLATE_URL)
                .queryParam("key", apiKey)
                .toUriString();

        JSONObject requestBody = new JSONObject();
        requestBody.put("q", text);
        requestBody.put("source", sourceLang);
        requestBody.put("target", targetLang);
        requestBody.put("format", "text");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(response.getBody());
                String translatedText = responseJson.getJSONObject("data")
                        .getJSONArray("translations")
                        .getJSONObject(0)
                        .getString("translatedText");

                // ★★★★★ 번역 성공 시, 이 부분에서 히스토리를 저장합니다. ★★★★★
                historyService.saveHistory(memberId, "번역", text, translatedText);
                log.info(memberId + " 사용자가 번역 기능을 사용했습니다.");

                return translatedText;
            } else {
                log.error("Google API Error: " + response.getStatusCode() + " | Body: " + response.getBody());
                return "Error: Google API returned status code " + response.getStatusCode();
            }
        } catch (Exception e) {
            log.error("API Call Exception: ", e);
            return "Error during API call: " + e.getMessage();
        }
    }
}