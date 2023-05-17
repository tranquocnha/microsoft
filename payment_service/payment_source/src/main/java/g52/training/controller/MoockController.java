package g52.training.controller;


import g52.training.event.CreateAccountPaymentFailEvent;
import g52.training.event.CreateAccountPaymentSuccessEvent;
import g52.training.event.PaymentBookingEvent;
import g52.training.repository.AccountJpaRepository;
import g52.training.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(value = "/moock")
public class MoockController {
    @Autowired
    RabbitMQSender<CreateAccountPaymentSuccessEvent> createAccountPaymentSuccessSender;

    @Autowired
    RabbitMQSender<CreateAccountPaymentFailEvent> createAccountPaymentFailSender;

    @Autowired
    AccountJpaRepository paymentJpaRepository;

    @GetMapping(value = "/publish-event/payment-booking")
    public String producer(@RequestParam("userId") String userId,
                           @RequestParam("bookingId") String bookingId,
                           @RequestParam("amount") BigDecimal amount,
                           @RequestParam("traceId") String traceId
                           ) {

        CreateAccountPaymentSuccessEvent successEvent = new CreateAccountPaymentSuccessEvent(traceId, userId, bookingId, 123456789L, createAccountPaymentSuccessSender);
        CreateAccountPaymentFailEvent failEvent = new CreateAccountPaymentFailEvent(traceId, userId, bookingId, 123456789L, createAccountPaymentFailSender);

        // Only pass with successEvent
        successEvent.fire();
//        failEvent.fire();

        return "Message sent to the RabbitMQ Successfully a PaymentBookingEvent with traceID  " + traceId;
    }
//    http://localhost:8089/moock/publish-event/payment-booking?userId=d215b5f8-0249-4dc5-89a3-51fd148cfb21&bookingId=d215b666-0249-4dc5-89a3-51fd148cfb21&amount=100&traceId=7775b5f8-0249-4dc5-89a3-51fd148cfb21

}
