package com.learning.ai.smart_loan_advisor.controller;

import com.learning.ai.smart_loan_advisor.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@RestController
@RequestMapping("/ai")
public class ChatController {

    public record SearchRequest(String userText) {};

    @Autowired
    private ChatService chatService;

    @GetMapping("/default-chat-client")
    public ResponseEntity<String> chatApiWithDefaultChatClient(@RequestParam("query") String query) {
        String queryResponse = chatService.getAnswerByDefaultChatClient(query);
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/query-with-context")
    public ResponseEntity<String> getAnswerBasedOnContextUsingDefaultChatClient(
            @RequestParam("query") String query,
            @RequestParam("contextKey") String contextKey,
            @RequestParam("contextValue") String contextValue) {
        String response = chatService.getAnswerBasedOnContextUsingDefaultChatClient(query, contextKey, contextValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/custom-chat-client")
    public ResponseEntity<String> chatApiWithCustomChatClient(@RequestParam("query") String query) {
        String queryResponse = chatService.getAnswerByCustomChatClient(query);
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/chat-response-obj")
    public ResponseEntity<ChatResponse> chatApiWithChatResponseObj(@RequestParam("query") String query) {
        ChatResponse queryResponse = chatService.getAnswerInChatResponseObj(query);
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/actor-movies-entity")
    public ResponseEntity<Record> getMoviesInfoForActor(@RequestParam("actorName") String actorName) {
        Record actorMovies = chatService.getMoviesInfoForActor(actorName);
        return ResponseEntity.ok(actorMovies);
    }

    @GetMapping("/return-list")
    public ResponseEntity<List<String>> getMoviesListForActor(@RequestParam("actorName") String actorName) {
        List<String> actorMovies = chatService.getMoviesListForActor(actorName);
        return ResponseEntity.ok(actorMovies);
    }

    @GetMapping("/author-books-entity")
    public ResponseEntity<Record> getBooksInfoForWriter(@RequestParam("authorName") String authorName) {
        Record authorBooks = chatService.getBooksInfoForWriter(authorName);
        return ResponseEntity.ok(authorBooks);
    }

    @GetMapping("/author-books-using-stream")
    public ResponseEntity<Record> chatWithStreaming(@RequestParam("authorName") String authorName) {
        Record authorBooks = chatService.getBooksInfoUsingStream(authorName);
        return ResponseEntity.ok(authorBooks);
    }



    @PostMapping("/emi-calculator")
    public ResponseEntity<String> emiCalculator(@RequestBody SearchRequest searchRequest) {
        String queryResponse = chatService.emiCalculator(searchRequest.userText());
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/chat-using-prompt-template")
    public ResponseEntity<String> chatUsingPromptTemplate(@RequestParam("composer") String composer) {
        String response = chatService.chatUsingPromptTemplate(composer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/hr-enquiry")
    public ResponseEntity<String> hrEnquiry(@RequestBody SearchRequest searchRequest) {
        String queryResponse = chatService.getHrResponse(searchRequest.userText());
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/chat-with-multi-modal-api")
    public ResponseEntity<String> chatWithMultimodal() {
        String response = chatService.chatWithMultiModalAPI();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAiResponse(@RequestParam String query) {
        return chatService.streamAiResponse(query);
    }

    @PostMapping("/chat-using-in-memory-history")
    public ResponseEntity<String> chatWithInMemory(@RequestBody SearchRequest searchRequest) {
        String queryResponse = chatService.chatUsingInMemoryConversationHistory(searchRequest.userText());
        return ResponseEntity.ok(queryResponse);
    }

    @PostMapping("/chat-using-h2-memory-history")
    public ResponseEntity<String> chatWithH2Memory(@RequestBody SearchRequest searchRequest) {
        String queryResponse = chatService.chatUsingH2ConversationHistory(searchRequest.userText());
        return ResponseEntity.ok(queryResponse);
    }

    @PostMapping("/chat-using-mysql-memory-history")
    public ResponseEntity<String> chatWithMysqlMemory(@RequestBody SearchRequest searchRequest) {
        String queryResponse = chatService.chatUsingMysqlConversationHistory(searchRequest.userText());
        return ResponseEntity.ok(queryResponse);
    }

    @GetMapping("/query-with-jdbc-memory")
    public ResponseEntity<String> getAnswerBasedOnContextUsingSqlDbChatClient(
            @RequestParam("query") String query,
            @RequestParam("contextKey") String contextKey,
            @RequestParam("contextValue") String contextValue,
            @RequestHeader("userId") String userId) {
        String response = chatService.getAnswerBasedOnContextUsingSqlDbChatClient(query, userId, contextKey, contextValue);
        return ResponseEntity.ok(response);
    }


}
