package com.learning.ai.smart_loan_advisor.config.memory.inmemory;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 18-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class InMemoryMemoryAdvisorConfig {

    @Autowired
    private ChatMemory inMemoryChatMemory;

    @Bean
    public MessageChatMemoryAdvisor inMemoryMessageChatMemoryAdvisor() {
        return MessageChatMemoryAdvisor.builder(inMemoryChatMemory).build();
    }
}
