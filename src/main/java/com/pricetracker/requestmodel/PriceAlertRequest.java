package com.pricetracker.requestmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class PriceAlertRequest {
    @NotBlank(message = "Product URL is required")
    private String productUrl;

    @NotNull(message = "Desired price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal desiredPrice;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Frequency is required")
    private String frequency; // "24_hours", "morning", "midnight"

    public PriceAlertRequest() {}

    public PriceAlertRequest(String productUrl, BigDecimal desiredPrice, String userId, String frequency) {
        this.productUrl = productUrl;
        this.desiredPrice = desiredPrice;
        this.userId = userId;
        this.frequency = frequency;
    }

    // Getters and Setters
    public String getProductUrl() { return productUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public BigDecimal getDesiredPrice() { return desiredPrice; }
    public void setDesiredPrice(BigDecimal desiredPrice) { this.desiredPrice = desiredPrice; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
}