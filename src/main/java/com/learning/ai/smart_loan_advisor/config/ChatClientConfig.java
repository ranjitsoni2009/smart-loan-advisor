package com.learning.ai.smart_loan_advisor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * Created by Ranjit Soni on 09-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class ChatClientConfig {

    @Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;

    /**
     * Advisor configured during ChatClient build
     * @return ChatClient
     */
    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(
                        messageChatMemoryAdvisorForInMemory(),
                        simpleLoggerAdvisor())
                .build();
    }

    @Bean
    public ChatClient chatClientWithJdbcChatMemory(ChatClient.Builder builder) {
        return builder.defaultAdvisors(
                        messageChatMemoryAdvisorForSqlDB(),
                        simpleLoggerAdvisor())
                .build();
    }



    @Bean
    public ChatClient customChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are GK Assistant, who will help to answer GK related query, " +
                "If any query is not in your knowledge base then deny the request with respect. In case of denial, Just mention before your response" +
                ", I have knowledge cut off of 2021, then include your answer").build();
    }

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor(
                request -> {
                    assert request != null;
                    return "Request --> "+request.prompt().getInstructions();
                },
                response -> {
                    assert response != null;
                    return "Response: " + Objects.requireNonNull(response.getResult()).getOutput();
                },
                1
        );
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(4)
                .build();
    }

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisorForInMemory() {
        return MessageChatMemoryAdvisor.builder(chatMemory()).order(0).build();
    }

    @Bean
    public ChatMemory chatMemoryFromSqlDB() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(6)
                .build();
    }

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisorForSqlDB() {
        return MessageChatMemoryAdvisor.builder(chatMemoryFromSqlDB()).build();
    }
}
