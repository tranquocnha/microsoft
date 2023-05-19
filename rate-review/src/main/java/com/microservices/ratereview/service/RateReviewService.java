package com.microservices.ratereview.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.entity.HistoryRateReviewEntity;
import com.microservices.ratereview.exception.ResourceNotFoundException;
import com.microservices.ratereview.repository.RateReviewRepository;

@Service
public class RateReviewService {

    private Logger logger = LoggerFactory.getLogger(RateReviewService.class);
    @Autowired
    private RateReviewRepository rateReviewRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Get all
    public List<HistoryRateReviewDTO> getHistoryRateReview() {
        return rateReviewRepository.findAll().stream().map(hsEn -> modelMapper.map(hsEn, HistoryRateReviewDTO.class))
                .collect(Collectors.toList());
    }

    // Create new history after booking car
    public void createHistoryRateAndReview(HistoryRateReviewDTO dto) {
        try {
            HistoryRateReviewEntity entity = modelMapper.map(dto, HistoryRateReviewEntity.class);
            rateReviewRepository.save(entity);
        } catch (Exception e) {
            logger.error("Exception: " + e.getStackTrace());
        }
        logger.info("Sussess");
    }

    // Avg rate for vehicle
    public int avgRateNumVehicle(int idVehicle) {
        int count = 0;
        try {
            count = rateReviewRepository.avgRateNumVehicle(idVehicle);
        } catch (Exception e) {
            logger.error("Exception: " + e.getStackTrace());
        }
        logger.info("Sussess");
        return count;
    }

    // Get review by id Vehicle
    public List<HistoryRateReviewDTO> getReviewVehicle(int idVehicle) {
        return rateReviewRepository.findByIdVehicle(idVehicle).stream().map(hsEn -> modelMapper.map(hsEn, HistoryRateReviewDTO.class))
                .collect(Collectors.toList());
    }
    
    // Create review 
    public HistoryRateReviewEntity createReview(int idLog, HistoryRateReviewDTO dto) {
    	HistoryRateReviewEntity createReviewEntity = rateReviewRepository.findByIdLog(idLog);
    	createReviewEntity.setReviewContent(dto.getReviewContent());
		createReviewEntity.setNumRate(dto.getNumRate());
        
        return rateReviewRepository.save(createReviewEntity);
    }
}
