package com.busanit501.translationproject.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParaphraseService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String paraphrase(String text, String language) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String langInstruction = "";

        switch (language) {
            case "ko":
                langInstruction = "한국어로 패러프레이징 해줘. 반드시 한국어로만 답변해야 합니다.";
                break;
            case "en":
                langInstruction = "Paraphrase this in English. You must respond only in English.";
                break;
            case "ja":
                langInstruction = "日本語に言い換えてください。必ず日本語でだけ答えてください。";
                break;
            case "zh": // 중국어 (간체, 번체 모두 zh 코드로 처리)
                langInstruction = "用中文改写这个句子。必须只用中文回答。";
                break;
            case "fr":
                langInstruction = "Reformulez cette phrase en français. Vous devez répondre uniquement en français.";
                break;
            case "ar":
                langInstruction = "أعد صياغة هذا باللغة العربية. يجب أن تجيب باللغة العربية فقط.";
                break;
            case "nl": // 네덜란드어
                langInstruction = "Parafraseer dit in het Nederlands. U moet alleen in het Nederlands antwoorden.";
                break;
            case "de": // 독일어
                langInstruction = "Formulieren Sie dies auf Deutsch um. Sie dürfen nur auf Deutsch antworten.";
                break;
            case "hi": // 힌디어
                langInstruction = "इसे हिंदी में बदलें। आपको केवल हिंदी में जवाब देना होगा।";
                break;
            case "id": // 인도네시아어
                langInstruction = "Parafrasekan ini dalam Bahasa Indonesia. Anda harus menjawab hanya dalam Bahasa Indonesia.";
                break;
            case "it": // 이탈리아어
                langInstruction = "Parafrasa questa frase in italiano. Devi rispondere solo in italiano.";
                break;
            case "pl": // 폴란드어
                langInstruction = "Parafrazuj to po polsku. Musisz odpowiedzieć tylko po polsku.";
                break;
            case "pt": // 포르투갈어
                langInstruction = "Parafraseie isso em português. Você deve responder apenas em português.";
                break;
            case "ru": // 러시아어
                langInstruction = "Перефразируйте это на русском языке. Вы должны отвечать только на русском.";
                break;
            case "es": // 스페인어
                langInstruction = "Reformule esto en español. Debe responder solo en español.";
                break;
            case "sv": // 스웨덴어
                langInstruction = "Parafrasera detta på svenska. Du måste svara endast på svenska.";
                break;
            case "th": // 태국어
                langInstruction = "ถอดความนี้เป็นภาษาไทย. คุณต้องตอบเป็นภาษาไทยเท่านั้น.";
                break;
            case "tr": // 터키어
                langInstruction = "Bunu Türkçe olarak yeniden ifade edin. Yalnızca Türkçe yanıt vermelisiniz.";
                break;
            case "vi": // 베트남어
                langInstruction = "Diễn đạt lại câu này bằng tiếng Việt. Bạn phải trả lời chỉ bằng tiếng Việt.";
                break;
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
                        .getString("content");
                return paraphrasedText.trim();
            } else {
                return "Error: OpenAI API returned status code " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Error during API call: " + e.getMessage();
        }
    }
}