package com.spring_ai.learning.smart_loan_advisor.controller;

import com.spring_ai.learning.smart_loan_advisor.service.ChatService;
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

    @GetMapping("/chat")
    public ResponseEntity<String> chatApi(@RequestParam("query") String query) {
        String queryResponse = chatService.getAnswer(query);
        return ResponseEntity.ok(queryResponse);
    }
}
