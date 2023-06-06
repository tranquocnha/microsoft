package com.microservices.ratereview.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.entity.HistoryRateReviewEntity;
import com.microservices.ratereview.exception.ResourceException;
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
        	logger.error("Exception:" + e); 
        }
        logger.info("Sussess");
    }

    // Avg rate for vehicle
    public int avgRateNumVehicle(String idVehicle) {
        int count = 0;
        try {
            count = rateReviewRepository.avgRateNumVehicle(idVehicle);
        } catch (Exception e) {
        	logger.error("Exception:" + e); 
        }
        logger.info("Sussess");
        return count;
    }
    // Avg rate for vehicle
    public int checkExistBooking(String idBooking) {
        int count = 0;
        try {
            count = rateReviewRepository.countByIdBooking(idBooking);
        } catch (Exception e) {
        	logger.error("Exception:" + e); 
        }
        return count;
    }
    // Get review by id Vehicle
    public List<HistoryRateReviewDTO> getReviewVehicle(String idVehicle) {
        return rateReviewRepository.findByIdVehicleAndFlagReview(idVehicle, 2).stream().map(hsEn -> modelMapper.map(hsEn, HistoryRateReviewDTO.class))
                .collect(Collectors.toList());
    }
    
    // Create review 
    public HistoryRateReviewEntity createReview(int idLog, HistoryRateReviewDTO dto) {
    	HistoryRateReviewEntity createReviewEntity = rateReviewRepository.findByIdLog(idLog);
    	createReviewEntity.setReviewContent(dto.getReviewContent());
		createReviewEntity.setNumRate(dto.getNumRate());
		createReviewEntity.setFlagReview(dto.getFlagReview());
        return rateReviewRepository.save(createReviewEntity);
    }
    public void updateStatusBooking(String idBooking) {
    	HistoryRateReviewEntity createReviewEntity = rateReviewRepository.findByIdBooking(idBooking);
    	createReviewEntity.setStatusBooking("COMPLETED");
        rateReviewRepository.save(createReviewEntity);
    }
    
    //Get review by id User
    public List<HistoryRateReviewDTO> getReviewByUser(String idUser){
    	return rateReviewRepository.findByIdUserAndStatusBooking(idUser).stream().map(hsEn -> modelMapper.map(hsEn, HistoryRateReviewDTO.class))
                .collect(Collectors.toList());
    }
    
}
