package com.learning.ai.smart_loan_advisor.service;

import com.learning.ai.smart_loan_advisor.tool.LoanCalculatorTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Service
public class ChatService {

    @Value("classpath:/templates/hr-policy.st")
    private Resource systemResource;

    private final ChatClient defaultchatClient;
    private final ChatClient customChatClient;
    private final LoanCalculatorTool loanCalculatorTool;

    @Autowired
    public ChatService(
            @Qualifier("defaultChatClient") ChatClient defaultchatClient,
            @Qualifier("customChatClient") ChatClient customChatClient,
            LoanCalculatorTool loanCalculatorTool) {
        this.defaultchatClient = defaultchatClient;
        this.customChatClient = customChatClient;
        this.loanCalculatorTool = loanCalculatorTool;
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

        String content = String.join("", Objects.requireNonNull(flux.collectList().block()));
        return converter.convert(content);
    }

    /**
     * Advisor configured during ChatClient builder method during build in ChatClientConfig
     * CONVERSATION_ID set at runtime
     */
    public String chatUsingConversationHistory(String userText) {
        return defaultchatClient.prompt()
                .system("You are smart AI assistant, if you don't know answer, Deny request respectfully with quick short statement.")
                .user(usr -> usr.text(userText))
                .advisors(advSpec -> advSpec.param(ChatMemory.CONVERSATION_ID, "123ABC"))
                .call()
                .content();
    }

    public String emiCalculator(String userText) {
        return defaultchatClient.prompt()
                .system("You are smart AI assistant, if you don't know answer, Deny request respectfully with quick short statement.")
                .user(usr -> usr.text(userText))
                .advisors(advSpec -> advSpec.param(ChatMemory.CONVERSATION_ID, "123ABC"))
                .tools(loanCalculatorTool)
                .call()
                .content();
    }

    public String chatUsingPromptTemplate(String composer) {
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template("""
                            Tell me the name of 5 movie whose soundtrack was composed by <compose>
                        """)
                .build();

        String prompt = promptTemplate.render(Map.of("compose", composer));
        return defaultchatClient.prompt(prompt)
                .advisors(advSpec -> advSpec.param(ChatMemory.CONVERSATION_ID, "123ABC"))
                .call()
                .content();
    }

    public String getHrResponse(String userQuery) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemResource);
        Message sysMessage = systemPromptTemplate.createMessage();
        Message userMessage = new UserMessage(userQuery);

        Prompt prompt = new Prompt(List.of(userMessage, sysMessage));
        return defaultchatClient.prompt(prompt)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, "13444"))
                .call()
                .content();
    }
}
