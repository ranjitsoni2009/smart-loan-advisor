package com.spring_ai.learning.smart_loan_advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Service
public class ChatService {

    private final ChatClient defaultchatClient;
    private final ChatClient customChatClient;

    @Autowired
    public ChatService(
            @Qualifier("defaultChatClient") ChatClient defaultchatClient,
            @Qualifier("customChatClient") ChatClient customChatClient) {
        this.defaultchatClient = defaultchatClient;
        this.customChatClient = customChatClient;
    }

    public String getAnswerByDefaultChatClient(String query) {
        return defaultchatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    public String getAnswerByCustomChatClient(String query) {
        return customChatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    public ChatResponse getAnswerInChatResponseObj(String query) {
        return customChatClient.prompt()
                .user(query)
                .call()
                .chatResponse();
    }

    public Record getMoviesInfoForActor(String actorName) {
        record ActorFilms(String actor, List<String> movies) {}
        return customChatClient.prompt()
                .user("Generate the filmography for a "+actorName+" actor.")
                .call()
                .entity(ActorFilms.class);
    }
}
