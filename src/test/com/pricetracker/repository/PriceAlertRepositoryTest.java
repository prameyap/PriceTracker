package com.pricetracker.repository;

import com.pricetracker.domain.PriceAlert;
import com.pricetracker.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@DataJpaTest
@ActiveProfiles("test")
class PriceAlertRepositoryTest {

    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindPriceAlert() {
        // Given
        Product product = new Product("test-id", "https://test.com", new BigDecimal("99.99"));
        PriceAlert alert = new PriceAlert("test-user", product, "morning");

        // When
        PriceAlert saved = priceAlertRepository.save(alert);
        entityManager.flush();

        // Then
        assertNotNull(saved.getId());
        assertEquals("test-user", saved.getUserId());
        assertTrue(saved.getIsActive());
    }

    @Test
    void shouldFindActiveAlertsOnly() {
        // Given
        Product product1 = new Product("test-id-1", "https://test1.com", new BigDecimal("99.99"));
        Product product2 = new Product("test-id-2", "https://test2.com", new BigDecimal("149.99"));

        PriceAlert activeAlert = new PriceAlert("user1", product1,"morning");
        PriceAlert inactiveAlert = new PriceAlert("user2", product2,"midnight");
        inactiveAlert.setIsActive(false);

        priceAlertRepository.save(activeAlert);
        priceAlertRepository.save(inactiveAlert);
        entityManager.flush();

        // When
        List<PriceAlert> activeAlerts = priceAlertRepository.findAllActiveAlerts();

        // Then
        assertEquals(5, activeAlerts.size());
        assertTrue(activeAlerts.get(0).getIsActive());
        assertEquals("john.doe@email.com", activeAlerts.get(0).getUserId());
    }

    @Test
    void shouldFindAlertsByUserId() {
        // Given
        Product product = new Product("test-id", "https://test.com", new BigDecimal("99.99"));
        PriceAlert alert = new PriceAlert("specific-user", product, "morning");
        priceAlertRepository.save(alert);
        entityManager.flush();

        // When
        List<PriceAlert> userAlerts = priceAlertRepository.findByUserIdAndIsActiveTrue("specific-user");

        // Then
        assertEquals(1, userAlerts.size());
        assertEquals("specific-user", userAlerts.get(0).getUserId());
    }
}