package com.microservices.ratereview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservices.ratereview.entity.HistoryRateReviewEntity;

public interface RateReviewRepository extends JpaRepository<HistoryRateReviewEntity, Integer> {

    // Avg rate for vehicle
    @Query(value = "SELECT AVG(NUM_RATE) FROM TB_HISTORY_RATE_REVIEW WHERE ID_VEHICLE = ?1 AND FLAG_REVIEW = 2",
            nativeQuery = true)
    public int avgRateNumVehicle(String idVehicle);

    public List<HistoryRateReviewEntity> findByIdVehicle(String idVehicle);
    public List<HistoryRateReviewEntity> findByIdUser(String idUser);
    
    public HistoryRateReviewEntity findByIdLog(int idLog);
    public HistoryRateReviewEntity findByIdBooking(String idBooking);
    public int countByIdBooking(String idBooking);

}
