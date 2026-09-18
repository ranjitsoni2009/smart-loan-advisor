package com.learning.ai.smart_loan_advisor.config.memory.h2;

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
public class H2MemoryAdvisorConfig {

    @Autowired
    private ChatMemory h2ChatMemory;

    @Bean
    public MessageChatMemoryAdvisor h2MessageChatMemoryAdvisor() {
        return MessageChatMemoryAdvisor.builder(h2ChatMemory).build();
    }
}
