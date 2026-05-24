package com.ollive.llmchat.repository;

import com.ollive.llmchat.entity.InferenceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InferenceLogRepository
        extends JpaRepository<InferenceLog, Long> {
}