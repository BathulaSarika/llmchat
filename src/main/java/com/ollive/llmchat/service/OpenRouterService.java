package com.ollive.llmchat.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenRouterService {

    private final WebClient.Builder webClientBuilder;

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String askAI(String prompt) {

        try {

            WebClient webClient = webClientBuilder.build();

            Map<String, Object> requestBody = Map.of(
                    "model", "mistralai/mistral-7b-instruct",
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    )
            );

            JsonNode response = webClient.post()
                    .uri("https://openrouter.ai/api/v1/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            return response
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {

            e.printStackTrace();

            return "Failed to connect to OpenRouter AI.";
        }
    }
}