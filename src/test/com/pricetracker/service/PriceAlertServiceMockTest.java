package com.pricetracker.service;

import com.pricetracker.requestmodel.PriceAlertRequest;
import com.pricetracker.responsemodel.PriceAlertResponse;
import com.pricetracker.domain.PriceAlert;
import com.pricetracker.domain.Product;
import com.pricetracker.repository.PriceAlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceAlertServiceMockTest {

    @Mock
    private PriceAlertRepository priceAlertRepository;

    @Mock
    private ProductService productService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private PriceAlertService priceAlertService;

    private PriceAlertRequest testRequest;
    private PriceAlert testAlert;

    @BeforeEach
    void setUp() {
        testRequest = new PriceAlertRequest(
                "https://mock-test.com/product",
                new BigDecimal("99.99"),
                "mock-user",
                "24_hours"
        );

        Product testProduct = new Product("mock-product-id",
                testRequest.getProductUrl(),
                testRequest.getDesiredPrice());

        testAlert = new PriceAlert(testRequest.getUserId(), testProduct, testRequest.getFrequency());
        testAlert.setId(1L);
        testAlert.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreatePriceAlertWithMocks() {
        // Given
        when(priceAlertRepository.save(any(PriceAlert.class))).thenReturn(testAlert);

        // When
        PriceAlertResponse response = priceAlertService.createPriceAlert(testRequest);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getAlertId());
        assertEquals("https://mock-test.com/product", response.getProductUrl());
        assertEquals(new BigDecimal("99.99"), response.getDesiredPrice());
        assertEquals("24_hours", response.getFrequency());

        // Verify interactions
        verify(priceAlertRepository).save(any(PriceAlert.class));
    }

    @Test
    void shouldCallRepositoryForActiveAlerts() {
        // When
        priceAlertService.getAllActiveAlerts("morning");

        // Then
        verify(priceAlertRepository).findByFrequencyAndIsActiveTrue("morning");
    }
}