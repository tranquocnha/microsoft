package com.fpt.g52.carsharing.booking.application.internal.commandservices;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.fpt.g52.carsharing.booking.domain.exceptions.NotFoundException;
import com.fpt.g52.carsharing.booking.domain.exceptions.ResourceInvalidException;
import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.model.commands.BookingCommand;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.BookingStatus;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.PaymentStatus;
import com.fpt.g52.carsharing.booking.domain.repositories.BookingRepository;

@Service
public class BookingCommandService {

    private final BookingRepository repository;

    public BookingCommandService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking book(BookingCommand command) throws Exception {
        
    	boolean isExist = repository.findByVehicleIdAndDuration(command.getVehicleId(), command.getBookingFrom(), command.getBookingTo()).isEmpty() ;
    	if (!isExist) {
    		throw new ResourceInvalidException("duaration invalid! Please choose other duaration. ");
    	}
    	
        Booking booking = new Booking(command);
        return repository.save(booking);
    }

    public void receive(String id) {
    	
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new );
        
        checkValidBookingByUserId(booking);
        
        // check valid receive car
        if (!(booking.getStatus().name().equals(BookingStatus.BOOKED.name()) 
        		&& booking.getPaymentStatus().name().equals(PaymentStatus.COMPLETED.name()))) {
        	throw new ResourceInvalidException("Could not receive car. You only receive after payment complete!");
        }
        
		long firstReceiveTm = booking.getDuration().getFrom() - (60 * 60 * 1000);
		if (System.currentTimeMillis() < firstReceiveTm) {
			throw new ResourceInvalidException("Could not receive car. you can receive after "
					+ Instant.ofEpochMilli(firstReceiveTm)
							.atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		}
        
        
        booking.receive();
        repository.save(booking);
    }

    public void complete(String id) {
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new);
        checkValidBookingByUserId(booking);
        
        if (!booking.getStatus().name().equals(BookingStatus.RECEIVED.name())) {
        	throw new ResourceInvalidException("Could not give car back! Please check Booking status");
        }
        
        booking.complete();
        repository.save(booking);
    }
    
    public void payComplete(String id) {
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new);
        
        if (!booking.getStatus().name().equals(BookingStatus.BOOKED.name())) {
        	throw new ResourceInvalidException("Could payment complete! Please check Booking status");
        }
        checkValidBookingByUserId(booking);
        booking.payComplete();
        repository.save(booking);
    }
    
    /**
     * Check valid Booking with userLogin
     * 
     * @param booking
     */
    private void checkValidBookingByUserId(Booking booking) {
        String userLogin = "admin";// get from token
        if(!userLogin.equals(booking.getUser().getId())) {
        	throw new ResourceInvalidException("BookingId invalid! ");
        }
    }
}
