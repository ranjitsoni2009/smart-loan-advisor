package com.learning.ai.smart_loan_advisor.config.common.h2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 18-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class H2MemoryChatClientConfig {

    @Autowired
    private SimpleLoggerAdvisor simpleLoggerAdvisor;

    @Autowired
    private MessageChatMemoryAdvisor h2MessageChatMemoryAdvisor;

    @Bean
    public ChatClient h2MemoryChatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(
                h2MessageChatMemoryAdvisor, simpleLoggerAdvisor)
                .build();
    }
}
