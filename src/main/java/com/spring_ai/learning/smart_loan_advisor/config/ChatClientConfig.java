package com.spring_ai.learning.smart_loan_advisor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 09-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
