package com.example.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret-key}")
    private String secretKey;

    @Value("${recaptcha.score-threshold}")
    private double scoreThreshold;

    private final RestClient restClient = RestClient.create();

    public boolean isValid(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        Map<String, Object> response = restClient.post()
                .uri("https://www.google.com/recaptcha/api/siteverify?secret={secret}&response={token}", secretKey, token)
                .retrieve()
                .body(Map.class);

        if (response == null) {
            return false;
        }

        boolean success = Boolean.TRUE.equals(response.get("success"));
        double score = response.get("score") != null ? ((Number) response.get("score")).doubleValue() : 0.0;

        return success && score >= scoreThreshold;
    }
}