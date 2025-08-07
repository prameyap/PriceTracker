package com.pricetracker.service;

import com.pricetracker.domain.PriceAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceAlertScheduler {

    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;


    // Run at 9:00 AM every day
    @Scheduled(cron = "0 0 9 * * *")
    public void checkMorningAlerts() {
        List<PriceAlert> activeAlerts = priceAlertService.getAllActiveAlerts("morning");
        for (PriceAlert alert : activeAlerts) {
            checkPriceAndNotify(alert);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkMidnightAlerts() {
        List<PriceAlert> activeAlerts = priceAlertService.getAllActiveAlerts("midnight");
        for (PriceAlert alert : activeAlerts) {
            checkPriceAndNotify(alert);
        }
    }

    public void checkPriceAndNotify(PriceAlert alert) {
        BigDecimal currentPrice = productService.getCurrentPrice(alert.getProduct().getProductId());
        BigDecimal desiredPrice = alert.getProduct().getPrice();

        if (currentPrice.compareTo(desiredPrice) <= 0) {
            notificationService.sendPriceDropNotification(alert, currentPrice);
        }
    }
}