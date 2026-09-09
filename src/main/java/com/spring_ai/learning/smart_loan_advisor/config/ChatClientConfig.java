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

    @Bean
    public ChatClient customChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are GK Assistant, who will help to answer GK related query, " +
                "If any query is not in your knowledge base then deny the request with respect. In case of denial, Just mention before your response" +
                ", I have knowledge cut off of 2021, then include your answer").build();
    }
}
