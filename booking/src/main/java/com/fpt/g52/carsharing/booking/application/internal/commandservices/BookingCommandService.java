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
import com.fpt.g52.common_service.notification.model.shared.enums.Sender;
import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;

@Service
public class BookingCommandService {

    private final BookingRepository repository;
    

    private final RabbitMQService<Booking> mqService;
    private final RabbitMQService<OrderPayload> mqServiceToPayment;
    
	public BookingCommandService(BookingRepository repository, RabbitMQService<Booking> mqService,
			RabbitMQService<OrderPayload> mqServiceToPayment) {
		this.repository = repository;
		this.mqService = mqService;
		this.mqServiceToPayment = mqServiceToPayment;
	}

	public Booking book(BookingCommand command) throws Exception {
        
    	boolean isExist = repository.findByVehicleIdAndDuration(command.getVehicleId(), command.getBookingFrom(), command.getBookingTo()).isEmpty() ;
    	long currentTime = System.currentTimeMillis();
    	if (!isExist || currentTime > command.getBookingFrom() || currentTime > command.getBookingTo()) {
    		throw new ResourceInvalidException("duration invalid! Please choose other duration. ");
    	}
    	
        Booking booking = new Booking(command);
        OrderPayload noticePayload = new OrderPayload();
        noticePayload.setSender(Sender.BOOKING_SERVICE);
        noticePayload.setReceiver(booking.getAccount().getId());
        noticePayload.setBookingId(booking.getId());
        noticePayload.setPrice(booking.getPrice().getPrice());
        noticePayload.setPaymentStatus(com.fpt.g52.common_service.notification.model.shared.enums.PaymentStatus.WAITING);
//        mqService.sendtoPayment(booking);
        mqService.sendtoReview(booking);
        mqServiceToPayment.sendtoNotice(noticePayload);
       
        return repository.save(booking);
    }

    public void receive(String id, String userId) {
    	
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new );
        
        checkValidBookingByUserId(booking, userId);
        
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

    public void complete(String id, String userId) {
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new);
        checkValidBookingByUserId(booking, userId);
        
        if (!booking.getStatus().name().equals(BookingStatus.RECEIVED.name())) {
        	throw new ResourceInvalidException("Could not give car back! Please check Booking status");
        }
        
        booking.complete();
        mqService.sendtoReviewCmpt(booking);
        repository.save(booking);
    }
    
    public void payComplete(String id) {
        Booking booking = repository.findById(id).orElseThrow(NotFoundException::new);
        
        if (!booking.getStatus().name().equals(BookingStatus.BOOKED.name())) {
        	throw new ResourceInvalidException("Could payment complete! Please check Booking status");
        }
        //checkValidBookingByUserId(booking, userId);
        booking.payComplete();
        repository.save(booking);
    }
    
    /**
     * Check valid Booking with userLogin
     * 
     * @param booking
     */
    private void checkValidBookingByUserId(Booking booking, String userId) {
        if(!userId.equals(booking.getAccount().getId())) {
        	throw new ResourceInvalidException("BookingId had not exists! ");
        }
    }
}
