package com.learning.ai.smart_loan_advisor.service;

import com.learning.ai.smart_loan_advisor.request.WeatherRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Ranjit Soni on 29-09-2026.
 * Author: ranjitsoni2009@gmail.com
 */

@Service
public class WeatherService {

    public String getWeather(WeatherRequest weatherRequest) {
        String cityName = weatherRequest.getCityName();
        if (cityName == null || cityName.trim().isEmpty()) {
            return "Error: City name cannot be empty.";
        }

        // Convert to lowercase to ensure case-insensitive matching
        return switch (cityName.trim().toLowerCase()) {
            case "delhi" -> "Weather in Delhi: 26°C, Partly Cloudy, Humidity: 81%";
            case "london" -> "Weather in London: 14°C, Light Rain, Wind: 15 km/h";
            case "tokyo" -> "Weather in Tokyo: 22°C, Sunny, Sky is Clear";
            default -> "Weather data for '" + cityName + "' is currently unavailable in this mock implementation.";
        };
    }
}
