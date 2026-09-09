package com.spring_ai.learning.smart_loan_advisor.controller;

import com.spring_ai.learning.smart_loan_advisor.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/return-record-entity")
    public ResponseEntity<Record> getMoviesInfoForActor(@RequestParam("actorName") String actorName) {
        Record actorMovies = chatService.getMoviesInfoForActor(actorName);
        return ResponseEntity.ok(actorMovies);
    }
}
