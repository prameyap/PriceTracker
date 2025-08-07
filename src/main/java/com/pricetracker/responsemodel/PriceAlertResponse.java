package com.pricetracker.responsemodel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceAlertResponse {
    private String message;
    private Long alertId;
    private String productUrl;
    private BigDecimal desiredPrice;
    private String frequency;
    private LocalDateTime createdAt;

    public PriceAlertResponse() {}

    public PriceAlertResponse(String message, Long alertId, String productUrl,
                              BigDecimal desiredPrice, String frequency, LocalDateTime createdAt) {
        this.message = message;
        this.alertId = alertId;
        this.productUrl = productUrl;
        this.desiredPrice = desiredPrice;
        this.frequency = frequency;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getAlertId() { return alertId; }
    public void setAlertId(Long alertId) { this.alertId = alertId; }

    public String getProductUrl() { return productUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public BigDecimal getDesiredPrice() { return desiredPrice; }
    public void setDesiredPrice(BigDecimal desiredPrice) { this.desiredPrice = desiredPrice; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}