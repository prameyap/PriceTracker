package com.pricetracker.service;

import com.pricetracker.domain.PriceAlert;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class NotificationService {

    public void sendPriceDropNotification(PriceAlert alert, BigDecimal currentPrice) {
        // In a real application, this would send email, SMS, or push notification
        String message = String.format(
                "Price Alert! Product at %s is now available for $%.2f (your target: $%.2f)",
                alert.getProduct().getProductUrl(),
                currentPrice,
                alert.getProduct().getPrice()
        );

        System.out.println("NOTIFICATION to user " + alert.getUserId() + ": " + message);

        // Simulate notification sent - in real app would integrate with email service
        logNotification(alert.getUserId(), message);
    }

    private void logNotification(String userId, String message) {
        // Log notification for testing purposes
        System.out.println("[NOTIFICATION LOG] User: " + userId + " | Message: " + message);
    }
}