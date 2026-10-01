package com.learning.ai.smart_loan_advisor.tool.config;

import com.learning.ai.smart_loan_advisor.request.WeatherRequest;
import com.learning.ai.smart_loan_advisor.service.WeatherService;
import com.openai.models.beta.threads.runs.steps.FunctionToolCall;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ranjit Soni on 29-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Configuration
public class WeatherToolConfig {

    @Bean
    public ToolCallback weatherToolCallback(WeatherService weatherService) {

        return FunctionToolCallback.builder("currentWeather", weatherService::getWeather)
                .description("Get the weather of city")
                .inputType(WeatherRequest.class)
                .build();
    }
}
