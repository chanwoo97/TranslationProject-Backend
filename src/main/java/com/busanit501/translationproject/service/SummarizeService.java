package com.busanit501.translationproject.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SummarizeService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String summarize(String text, String language) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        String langInstruction;
        switch (language) {
            case "ko":
                langInstruction = "한국어로 요약해줘. 반드시 한국어로만 답변해야 합니다.";
                break;
            case "en":
                langInstruction = "Summarize in English. You must respond only in English.";
                break;
            case "ja":
                langInstruction = "日本語で要約してください。必ず日本語のみで回答してください。";
                break;
            case "zh":
                langInstruction = "请用中文进行摘要。必须只用中文回答。";
                break;
            case "fr":
                langInstruction = "Résumez en français. Vous devez répondre uniquement en français.";
                break;
            case "ar":
                langInstruction = "لخص هذا باللغة العربية. يجب أن تجيب باللغة العربية فقط.";
                break;
            case "nl":
                langInstruction = "Vat dit samen in het Nederlands. U moet alleen in het Nederlands antwoorden.";
                break;
            case "de":
                langInstruction = "Fassen Sie dies auf Deutsch zusammen. Sie dürfen nur auf Deutsch antworten.";
                break;
            case "hi":
                langInstruction = "इसे हिंदी में संक्षेप में बताएं। आपको केवल हिंदी में जवाब देना होगा।";
                break;
            case "id":
                langInstruction = "Ringkas ini dalam Bahasa Indonesia. Anda harus menjawab hanya dalam Bahasa Indonesia.";
                break;
            case "it":
                langInstruction = "Riassumi questo in italiano. Devi rispondere solo in italiano.";
                break;
            case "pl":
                langInstruction = "Podsumuj to po polsku. Musisz odpowiedzieć tylko po polsku.";
                break;
            case "pt":
                langInstruction = "Resuma em português. Você deve responder apenas em português.";
                break;
            case "ru":
                langInstruction = "Резюмируйте это на русском языке. Вы должны отвечать только на русском.";
                break;
            case "es":
                langInstruction = "Resume en español. Debes responder solo en español.";
                break;
            case "sv":
                langInstruction = "Sammanfatta detta på svenska. Du måste svara endast på svenska.";
                break;
            case "th":
                langInstruction = "สรุปสิ่งนี้เป็นภาษาไทย คุณต้องตอบเป็นภาษาไทยเท่านั้น";
                break;
            case "tr":
                langInstruction = "Bunu Türkçe olarak özetleyin. Yalnızca Türkçe yanıt vermelisiniz.";
                break;
            case "vi":
                langInstruction = "Tóm tắt điều này bằng tiếng Việt. Bạn phải trả lời chỉ bằng tiếng Việt.";
                break;
            default:
                langInstruction = "한국어로 요약해줘. 반드시 한국어로만 답변해야 합니다.";
                break;
        }

        try {
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "당신은 모든 언어를 지원하는 요약 전문가입니다.");

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", "다음 텍스트를 핵심만 간결하게 요약해줘. " + langInstruction + "\n---\n" + text);

            JSONArray messages = new JSONArray();
            messages.put(systemMessage);
            messages.put(userMessage);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("messages", messages);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(response.getBody());
                String summarized = responseJson
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
                return summarized.trim();
            }
            return "Error: OpenAI API returned status code " + response.getStatusCode();
        } catch (JSONException e) {
            return "Error building request: " + e.getMessage();
        } catch (Exception e) {
            return "Error during API call: " + e.getMessage();
        }
    }
}


