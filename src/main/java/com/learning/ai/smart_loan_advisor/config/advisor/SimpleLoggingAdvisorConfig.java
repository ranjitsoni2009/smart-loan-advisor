package com.learning.ai.smart_loan_advisor.config.advisor;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 18-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class SimpleLoggingAdvisorConfig {

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return SimpleLoggerAdvisor.builder()
                .requestToString(chatClientRequest -> chatClientRequest.prompt().getInstructions().toString())
                .responseToString(chatResponse -> chatResponse.getResult().getOutput().toString())
                .build();
    }
}
