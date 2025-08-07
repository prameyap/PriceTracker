package com.pricetracker.service;

import com.pricetracker.requestmodel.PriceAlertRequest;
import com.pricetracker.responsemodel.PriceAlertResponse;
import com.pricetracker.domain.PriceAlert;
import com.pricetracker.repository.PriceAlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PriceAlertServiceDatabaseTest {
    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Test
    void shouldCreatePriceAlertWithDatabasePersistence() {
        // Given
        PriceAlertRequest request = new PriceAlertRequest(
                "https://unittest.com/new-product",
                new BigDecimal("199.99"),
                "database-test-user",
                "morning"
        );

        // When
        PriceAlertResponse response = priceAlertService.createPriceAlert(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getAlertId());
        assertEquals("Price alert has been successfully set! You will be notified when the price drops to or below your desired price.",
                response.getMessage());

        // Verify in database
        List<PriceAlert> userAlerts = priceAlertRepository.findByUserIdAndIsActiveTrue("database-test-user");
        assertEquals(1, userAlerts.size());

        PriceAlert savedAlert = userAlerts.get(0);
        assertEquals("database-test-user", savedAlert.getUserId());
        assertEquals("https://unittest.com/new-product", savedAlert.getProduct().getProductUrl());
        assertEquals(new BigDecimal("199.99"), savedAlert.getProduct().getPrice());
    }
}