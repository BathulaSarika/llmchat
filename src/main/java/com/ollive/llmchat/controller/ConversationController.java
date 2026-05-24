package com.ollive.llmchat.controller;

import com.ollive.llmchat.entity.ChatMessage;
import com.ollive.llmchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ConversationController {

    private final ChatService chatService;

    @PostMapping("/send")
    public ChatMessage sendMessage(
            @RequestBody Map<String, String> request
    ) {

        return chatService.sendMessage(
                null,
                request.get("message")
        );
    }

    @GetMapping("/{sessionId}")
    public List<ChatMessage> getConversation(
            @PathVariable String sessionId
    ) {

        return chatService.getConversation(
                sessionId
        );
    }
}