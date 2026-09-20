package com.learning.ai.smart_loan_advisor.config.neo4j;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.neo4j.Neo4jChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 20-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class Neo4jMemoryChatMemoryConfig {

    @Bean
    public ChatMemory neo4jChatMemory(Neo4jChatMemoryRepository neo4jChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(neo4jChatMemoryRepository)
                .maxMessages(5)
                .build();
    }
}
