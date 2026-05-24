package com.ollive.llmchat.controller;

import com.ollive.llmchat.entity.InferenceLog;
import com.ollive.llmchat.repository.InferenceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final InferenceLogRepository repository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        List<InferenceLog> logs = repository.findAll();

        long totalRequests = logs.size();

        long success =
                logs.stream()
                        .filter(log ->
                                "SUCCESS".equals(log.getStatus()))
                        .count();

        long failed =
                logs.stream()
                        .filter(log ->
                                "FAILED".equals(log.getStatus()))
                        .count();

        double avgLatency =
                logs.stream()
                        .mapToLong(InferenceLog::getLatencyMs)
                        .average()
                        .orElse(0);

        Map<String, Object> response =
                new HashMap<>();

        response.put("totalRequests", totalRequests);

        response.put("successRequests", success);

        response.put("failedRequests", failed);

        response.put("averageLatency", avgLatency);

        response.put("logs", logs);

        return response;
    }
}