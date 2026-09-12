package com.learning.ai.smart_loan_advisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Service
public class ChatService {

    private final ChatClient defaultchatClient;
    private final ChatClient customChatClient;
    private final ChatMemory chatMemory;

    @Autowired
    public ChatService(
            @Qualifier("defaultChatClient") ChatClient defaultchatClient,
            @Qualifier("customChatClient") ChatClient customChatClient,
            ChatMemory chatMemory) {
        this.defaultchatClient = defaultchatClient;
        this.customChatClient = customChatClient;
        this.chatMemory = chatMemory;
    }

    public String getAnswerByDefaultChatClient(String query) {
        return defaultchatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    public String getAnswerBasedOnContextUsingDefaultChatClient(String query, String contextKey, String contextValue) {
        return defaultchatClient.prompt()
                .user(usr -> usr.text(query).metadata(contextKey, contextValue))
                .system( sys -> sys
                        .text(""" 
                                You are GK Smart Agent. Response the query based on given context {contextKey}:{contextValue}, if you don't have answer then deny respectfully.
                                """)
                        .param("contextKey", contextKey)
                        .param("contextValue", contextValue))
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

    public List<String> getMoviesListForActor(String actorName) {
        return defaultchatClient.prompt()
                .user("Generate the filmography for a "+actorName+" actor.")
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }

    public Record getBooksInfoForWriter(String authorName) {
        record AuthorBook(String authorName, List<String> books) {}
        return customChatClient.prompt()
                .user("Generate the books list for a "+authorName+" actor.")
                .call()
                .entity(AuthorBook.class, spec -> spec
                        .useProviderStructuredOutput()
                        .validateSchema());
    }

    /**
     * Example of Prompt Template with stream()
     * @param authorName
     * @return
     */
    public Record getBooksInfoUsingStream(String authorName) {
        record AuthorBook(String authorName, List<String> books) {}
        var converter = new BeanOutputConverter<>(AuthorBook.class);

        Flux<String> flux = this.customChatClient.prompt()
                .user(u -> u.text("""
                        Generate the book list for a author {authorName}.
                        {format}
                      """)
                        .param("authorName", authorName)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = Objects.requireNonNull(flux.collectList().block()).stream()
                .collect(Collectors.joining());

        return converter.convert(content);
    }

    public String chatUsingConversationHistory(String userText) {
        SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                request -> {
                    assert request != null;
                    return "Custom request: " + request.prompt().getUserMessage();
                },
                response -> {
                    assert response != null;
                    return "Custom response: " + Objects.requireNonNull(response.getResult()).getOutput();
                },
                0
        );

        Consumer<ChatClient.AdvisorSpec> advisor = advSpec -> advSpec
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory).build(), customLogger)
                .param(ChatMemory.CONVERSATION_ID, "123ABC");

        return defaultchatClient.prompt()
                .system("You are smart AI assistant, if you don't know answer, Deny request respectfully with quick short statement.")
                .user(usr -> usr.text(userText))
                .advisors(advisor)
                .call()
                .content();
    }
}
