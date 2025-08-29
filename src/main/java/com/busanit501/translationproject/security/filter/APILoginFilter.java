package com.busanit501.translationproject.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

//작업 순서12
@Log4j2
public class APILoginFilter extends AbstractAuthenticationProcessingFilter {

    // 생성자: 기본 필터 URL 설정
    public APILoginFilter(String defaultFilterProcessesUrl) {

        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        log.info("APILoginFilter - attemptAuthentication executed");

        if (request.getMethod().equalsIgnoreCase("GET")) {
            log.info("GET METHOD NOT SUPPORTED");
            return null;
        }

        Map<String, String> jsonData = parseRequestJSON(request);
        log.info("Parsed JSON Data: {}", jsonData);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        jsonData.get("memberId"),
                        jsonData.get("password")
                );

        return getAuthenticationManager().authenticate(authenticationToken);

    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        // JSON 데이터를 파싱하여 mid와 mpw 값을 Map으로 처리
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error("Error parsing JSON request: {}", e.getMessage());
        }
        return null;
    }
}
