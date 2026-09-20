package com.learning.ai.smart_loan_advisor.config.neo4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 20-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class Neo4jMemoryChatClientConfig {

    @Autowired
    private MessageChatMemoryAdvisor neo4jChatMemoryAdvisor;

    @Autowired
    private SimpleLoggerAdvisor simpleLoggerAdvisor;

    @Bean
    public ChatClient neo4jMemoryChatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(neo4jChatMemoryAdvisor, simpleLoggerAdvisor).build();
    }
}
