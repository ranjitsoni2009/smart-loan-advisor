package com.learning.ai.smart_loan_advisor.controller;

import com.learning.ai.smart_loan_advisor.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Ranjit Soni on 08-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@RestController
@RequestMapping("/ai")
public class ChatController {

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
}
