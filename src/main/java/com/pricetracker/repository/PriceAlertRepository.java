package com.pricetracker.repository;

import com.pricetracker.domain.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByUserIdAndIsActiveTrue(String userId);
    List<PriceAlert> findByFrequencyAndIsActiveTrue(String frequency);

    @Query("SELECT pa FROM PriceAlert pa WHERE pa.isActive = true")
    List<PriceAlert> findAllActiveAlerts();
}