package com.ollive.llmchat.service;

import com.ollive.llmchat.entity.InferenceLog;
import com.ollive.llmchat.repository.InferenceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InferenceLogService {

    private final InferenceLogRepository inferenceLogRepository;

    public void saveLog(
            String provider,
            String modelName,
            Long latencyMs,
            String status,
            String sessionId,
            String inputPreview,
            String outputPreview
    ) {

        InferenceLog log = InferenceLog.builder()
                .provider(provider)
                .modelName(modelName)
                .latencyMs(latencyMs)
                .status(status)
                .sessionId(sessionId)
                .inputPreview(inputPreview)
                .outputPreview(outputPreview)
                .createdAt(LocalDateTime.now())
                .build();

        inferenceLogRepository.save(log);
    }
}