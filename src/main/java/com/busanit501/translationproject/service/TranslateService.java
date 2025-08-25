package com.busanit501.translationproject.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TranslateService {

    @Value("${google.api.key}")
    private String apiKey;

    private static final String GOOGLE_TRANSLATE_URL = "https://translation.googleapis.com/language/translate/v2";

    public String translate(String text, String sourceLang, String targetLang) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Build the URL with the API key
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_TRANSLATE_URL)
                .queryParam("key", apiKey)
                .toUriString();

        // 2. Create the request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("q", text);
        requestBody.put("source", sourceLang);
        requestBody.put("target", targetLang);
        requestBody.put("format", "text");

        // 3. Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 4. Create the HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // 5. Make the API call
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // 6. Parse the response
                JSONObject responseJson = new JSONObject(response.getBody());
                return responseJson.getJSONObject("data")
                                   .getJSONArray("translations")
                                   .getJSONObject(0)
                                   .getString("translatedText");
            } else {
                return "Error: Google API returned status code " + response.getStatusCode() + " Body: " + response.getBody();
            }
        } catch (Exception e) {
            return "Error during API call: " + e.getMessage();
        }
    }
}
