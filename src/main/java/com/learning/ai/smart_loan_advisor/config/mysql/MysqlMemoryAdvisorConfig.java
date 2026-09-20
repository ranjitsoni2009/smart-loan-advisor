package com.learning.ai.smart_loan_advisor.config.mysql;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 19-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Configuration
public class MysqlMemoryAdvisorConfig {

    @Bean
    public MessageChatMemoryAdvisor mysqlMessageChatMemoryAdvisor(ChatMemory mysqlChatMemory) {
        return MessageChatMemoryAdvisor.builder(mysqlChatMemory).build();
    }
}
