package com.ollive.llmchat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InferenceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    private String modelName;

    private Long latencyMs;

    private String status;

    private String sessionId;

    @Column(columnDefinition = "TEXT")
    private String inputPreview;

    @Column(columnDefinition = "TEXT")
    private String outputPreview;

    private LocalDateTime createdAt;
}