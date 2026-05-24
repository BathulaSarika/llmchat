package com.ollive.llmchat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {

    private String sessionId;

    private String content;
}