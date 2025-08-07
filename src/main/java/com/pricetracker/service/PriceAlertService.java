package com.pricetracker.service;

import com.pricetracker.requestmodel.PriceAlertRequest;
import com.pricetracker.responsemodel.PriceAlertResponse;
import com.pricetracker.domain.PriceAlert;
import com.pricetracker.domain.Product;
import com.pricetracker.repository.PriceAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PriceAlertService {

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public PriceAlertResponse createPriceAlert(PriceAlertRequest request) {
        validatePriceAlertRequest(request);

        try {
        // Generate product ID from URL
        String productId = generateProductId(request.getProductUrl());

        // Create or update product
        Product product = new Product(productId, request.getProductUrl(),
                 request.getDesiredPrice());

        // Create price alert
        PriceAlert priceAlert = new PriceAlert(request.getUserId(), product, request.getFrequency());
        PriceAlert savedAlert = priceAlertRepository.save(priceAlert);

        return new PriceAlertResponse(
                "Price alert has been successfully set! You will be notified when the price drops to or below your desired price.",
                savedAlert.getId(),
                request.getProductUrl(),
                request.getDesiredPrice(),
                request.getFrequency(),
                savedAlert.getCreatedAt()
        );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create price alert: " + e.getMessage(), e);
        }
    }
    private void validatePriceAlertRequest(PriceAlertRequest request) {
        if (request.getDesiredPrice() == null || request.getDesiredPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (request.getProductUrl() == null || request.getProductUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Product URL cannot be empty");
        }

        if (!request.getProductUrl().startsWith("http")) {
            throw new IllegalArgumentException("Product URL must be a valid HTTP/HTTPS URL");
        }

        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }

        String frequency = request.getFrequency();
        if (frequency == null || (!frequency.equals("24_hours") && !frequency.equals("morning")
                && !frequency.equals("midnight") && !frequency.equals("every_6_hours") && !frequency.equals("daily"))) {
            throw new IllegalArgumentException("Frequency must be one of: 24_hours, morning, midnight, every_6_hours, daily");
        }
    }
    public List<PriceAlert> getAllActiveAlerts(String frequency) {
        return priceAlertRepository.findByFrequencyAndIsActiveTrue(frequency);
    }

    public List<PriceAlert> getUserAlerts(String userId) {
        return priceAlertRepository.findByUserIdAndIsActiveTrue(userId);
    }

    private String generateProductId(String productUrl) {
        // Simple URL to ID conversion - in real app would parse actual product ID
        return UUID.nameUUIDFromBytes(productUrl.getBytes()).toString();
    }
}