package com.ollive.llmchat.controller;

import com.ollive.llmchat.entity.InferenceLog;
import com.ollive.llmchat.repository.InferenceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InferenceLogController {

    private final InferenceLogRepository repository;

    @GetMapping
    public List<InferenceLog> getLogs() {

        return repository.findAll();
    }
}