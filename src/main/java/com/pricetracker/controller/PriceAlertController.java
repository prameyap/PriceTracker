package com.pricetracker.controller;

import com.pricetracker.requestmodel.PriceAlertRequest;
import com.pricetracker.responsemodel.PriceAlertResponse;
import com.pricetracker.service.PriceAlertService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/price-alerts")
public class PriceAlertController {
    @Autowired
    private PriceAlertService priceAlertService;

    @PostMapping
    public ResponseEntity<PriceAlertResponse> createPriceAlert(@Valid @RequestBody PriceAlertRequest request) {
        log.info("Creating price alert for user: {} and URL: {}", request.getUserId(), request.getProductUrl());
        PriceAlertResponse response = priceAlertService.createPriceAlert(request);
        log.info("Price alert created successfully with ID: {}", response.getAlertId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Price Tracker API is running!");
    }
}