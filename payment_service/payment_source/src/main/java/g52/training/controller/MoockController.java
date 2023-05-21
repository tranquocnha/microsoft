package g52.training.controller;


import g52.training.event.CreateBookingPaymentEvent;
import g52.training.event.CreateAccountPaymentFailEvent;
import g52.training.event.CreateAccountPaymentSuccessEvent;
import g52.training.repository.AccountJpaRepository;
import g52.training.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/moock")
public class MoockController {
    @Autowired
    RabbitMQSender<CreateAccountPaymentSuccessEvent> createAccountPaymentSuccessSender;

    @Autowired
    RabbitMQSender<CreateBookingPaymentEvent> createBookingPaymentEventRabbitMQSender;

    @Autowired
    AccountJpaRepository paymentJpaRepository;
//    private RabbitMQSender<CreateBookingPaymentEvent> ;

    @GetMapping(value = "/publish-event/payment-booking")
    public ResponseEntity<String> producer(@RequestBody CreateBookingPaymentEvent event) {

//        CreateAccountPaymentSuccessEvent successEvent = new CreateAccountPaymentSuccessEvent(traceId, userId, bookingId, 123456789L, createAccountPaymentSuccessSender);
//        CreateAccountPaymentFailEvent failEvent = new CreateAccountPaymentFailEvent(traceId, userId, bookingId, 123456789L, createAccountPaymentFailSender);
//
//        // Only pass with successEvent
//        successEvent.fire();
//        failEvent.fire();;

        return ResponseEntity.ok("Message sent to the RabbitMQ Successfully a PaymentBookingEvent with traceID  " + event.getId());
    }
//    http://localhost:8089/moock/publish-event/payment-booking?userId=d215b5f8-0249-4dc5-89a3-51fd148cfb21&bookingId=d215b666-0249-4dc5-89a3-51fd148cfb21&amount=100&traceId=7775b5f8-0249-4dc5-89a3-51fd148cfb21

}
