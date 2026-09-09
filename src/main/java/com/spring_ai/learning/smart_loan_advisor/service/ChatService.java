package com.spring_ai.learning.smart_loan_advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String getAnswer(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }
}
