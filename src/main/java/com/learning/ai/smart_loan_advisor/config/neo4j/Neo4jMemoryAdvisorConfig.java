package com.learning.ai.smart_loan_advisor.config.neo4j;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 20-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Configuration
public class Neo4jMemoryAdvisorConfig {

    @Bean
    public MessageChatMemoryAdvisor neo4jChatMemoryAdvisor(ChatMemory neo4jChatMemory) {
        return MessageChatMemoryAdvisor.builder(neo4jChatMemory).build();
    }
}
