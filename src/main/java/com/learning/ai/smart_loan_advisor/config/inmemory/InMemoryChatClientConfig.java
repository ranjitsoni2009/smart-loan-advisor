package com.learning.ai.smart_loan_advisor.config.inmemory;

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
public class InMemoryChatClientConfig {

    @Autowired
    private MessageChatMemoryAdvisor inMemoryMessageChatMemoryAdvisor;

    @Autowired
    private SimpleLoggerAdvisor simpleLoggerAdvisor;

    @Bean
    public ChatClient inMemoryChatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(
                inMemoryMessageChatMemoryAdvisor,
                simpleLoggerAdvisor)
                .build();
    }
}
