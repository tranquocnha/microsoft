package com.fpt.g52.carsharing.booking.interfaces.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fpt.g52.carsharing.booking.domain.exceptions.NotFoundException;
import com.fpt.g52.carsharing.booking.domain.exceptions.ResourceInvalidException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.notFound()
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleAllException(Exception ex) {
        return ResponseEntity.internalServerError()
                .build();
    }

    @ExceptionHandler(ResourceInvalidException.class)
    public ResponseEntity<ErrorMessage> handleResourceInvalidException(ResourceInvalidException ex) {
    	return new ResponseEntity<>(new ErrorMessage(Arrays.asList(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    record ErrorMessage(List<String> errorMessages) {
    }
}
