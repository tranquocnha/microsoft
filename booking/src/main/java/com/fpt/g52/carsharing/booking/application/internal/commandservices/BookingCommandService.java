package com.fpt.g52.carsharing.booking.application.internal.commandservices;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl.UserService;
import com.fpt.g52.carsharing.booking.application.internal.rabbitMQ.RabbitMQService;
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
    
    private final UserService userService;

    private final RabbitMQService<Booking> mqService;
    
    public BookingCommandService(BookingRepository repository, UserService userService, RabbitMQService<Booking> mqService) {
        this.repository = repository;
        this.userService = userService;
        this.mqService = mqService;
    }

    public Booking book(BookingCommand command) throws Exception {
        
    	boolean isExist = repository.findByVehicleIdAndDuration(command.getVehicleId(), command.getBookingFrom(), command.getBookingTo()).isEmpty() ;
    	if (!isExist) {
    		throw new ResourceInvalidException("duaration invalid! Please choose other duaration. ");
    	}
    	
        Booking booking = new Booking(command);
        mqService.sendtoPayment(booking);
        mqService.sendtoReview(booking);
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
        String userLogin = userService.getUserByid(null).getId();// get from token
        if(!userLogin.equals(booking.getAccount().getId())) {
        	throw new ResourceInvalidException("BookingId invalid! ");
        }
    }
}
