package com.pricetracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class ProductService {

    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private NotificationService notificationService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public BigDecimal getCurrentPrice(String productId) {
        try {
            // Load static JSON file
            ClassPathResource resource = new ClassPathResource("products.json");
            JsonNode productData = objectMapper.readTree(resource.getInputStream());

            // Find product in static data
            JsonNode products = productData.get("products");
            for (JsonNode product : products) {
                if (productId.equals(product.get("id").asText())) {
                    BigDecimal basePrice = new BigDecimal(product.get("price").asText());
                    return basePrice;
                }
            }
            throw new RuntimeException("Product not found");
        } catch (IOException e) {
            throw new RuntimeException("Error fetching price", e);
        }
    }
}