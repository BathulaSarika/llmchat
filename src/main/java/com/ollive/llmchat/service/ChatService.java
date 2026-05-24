package com.ollive.llmchat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ollive.llmchat.entity.ChatMessage;
import com.ollive.llmchat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    private final InferenceLogService inferenceLogService;

    private final WebClient.Builder webClientBuilder;

    @Value("${openrouter.api.key}")
    private String openRouterApiKey;

    public ChatMessage sendMessage(
            String sessionId,
            String content
    ) {

        long start = System.currentTimeMillis();

        ChatMessage userMessage =
                ChatMessage.builder()
                        .sessionId(sessionId)
                        .sender("USER")
                        .content(content)
                        .createdAt(LocalDateTime.now())
                        .build();

        chatMessageRepository.save(userMessage);

        String aiResponse;

        try {

            WebClient webClient = webClientBuilder.build();

            JsonNode response =
                    webClient.post()
                            .uri("https://openrouter.ai/api/v1/chat/completions")
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Bearer " + openRouterApiKey)
                            .header("HTTP-Referer",
                                    "http://localhost:8089")
                            .header("X-Title",
                                    "LLM Chat App")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(
                                    Map.of(
                                            "model", "openai/gpt-3.5-turbo",
                                            "messages", List.of(
                                                    Map.of(
                                                            "role", "user",
                                                            "content", content
                                                    )
                                            )
                                    )
                            )
                            .retrieve()
                            .bodyToMono(JsonNode.class)
                            .block();

            aiResponse =
                    response
                            .get("choices")
                            .get(0)
                            .get("message")
                            .get("content")
                            .asText();

        } catch (Exception e) {

            e.printStackTrace();

            aiResponse =
                    "Failed to connect to OpenRouter AI.";
        }

        ChatMessage aiMessage =
                ChatMessage.builder()
                        .sessionId(sessionId)
                        .sender("AI")
                        .content(aiResponse)
                        .createdAt(LocalDateTime.now())
                        .build();

        chatMessageRepository.save(aiMessage);

        long end = System.currentTimeMillis();

        inferenceLogService.saveLog(
                "OpenRouter",
                "openai/gpt-3.5-turbo",
                end - start,
                "SUCCESS",
                sessionId,
                content,
                aiResponse
        );

        return aiMessage;
    }

    public List<ChatMessage> getConversation(
            String sessionId
    ) {

        return chatMessageRepository
                .findBySessionIdOrderByCreatedAtAsc(
                        sessionId
                );
    }
}