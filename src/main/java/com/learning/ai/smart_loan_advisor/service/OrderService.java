package com.learning.ai.smart_loan_advisor.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * Created by Ranjit Soni on 01-10-2026.
 * Author: ranjitsoni2009@gmail.com
 */
@Service
public class OrderService {

    @Tool(description = "This function will return the order status based on orderId input value.", name = "getOrderStatus")
    public String getOrderStatus(@ToolParam(description = "this is order id") String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            return "INVALID_ORDER_ID";
        }

        // Mock statuses for demonstration
        String[] statuses = {"PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"};

        // Use the hash code of the orderId to deterministically pick a status,
        // or use new java.util.Random().nextInt() for a completely random one.
        int index = Math.abs(orderId.hashCode()) % statuses.length;

        return statuses[index];
    }

    @Tool(description = "This method will return approximate delivery time for given orderId", name = "getApproximateDeliveryTime")
    public String getApproximateDeliveryTime(@ToolParam(description = "this is order id") String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            return "Unknown (Invalid Order ID)";
        }

        // Use the hash code of the orderId so the same ID always returns the same delivery window
        int hash = Math.abs(orderId.hashCode());

        // Generate a dummy number of days to delivery (between 1 and 7 days)
        int daysToDelivery = (hash % 7) + 1;

        // Get the current date and add the calculated days
        java.time.LocalDate estimatedDate = java.time.LocalDate.now().plusDays(daysToDelivery);
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");

        return "Estimated delivery by " + estimatedDate.format(formatter) + " (in " + daysToDelivery + " days)";
    }

}
