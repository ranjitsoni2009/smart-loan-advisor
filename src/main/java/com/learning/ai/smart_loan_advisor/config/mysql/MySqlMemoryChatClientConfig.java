package com.learning.ai.smart_loan_advisor.config.mysql;

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
public class MySqlMemoryChatClientConfig {

    @Autowired
    private MessageChatMemoryAdvisor mysqlMessageChatMemoryAdvisor;

    @Autowired
    private SimpleLoggerAdvisor simpleLoggerAdvisor;

    @Bean
    public ChatClient mysqlMemoryChatClient(ChatClient.Builder builder) {
        return builder.defaultAdvisors(mysqlMessageChatMemoryAdvisor, simpleLoggerAdvisor).build();
    }
}
