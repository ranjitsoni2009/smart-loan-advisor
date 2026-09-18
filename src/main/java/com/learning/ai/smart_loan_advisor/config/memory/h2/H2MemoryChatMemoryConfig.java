package com.learning.ai.smart_loan_advisor.config.memory.h2;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 18-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class H2MemoryChatMemoryConfig {

    @Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;

    @Bean
    public ChatMemory h2ChatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(5)
                .build();
    }
}
