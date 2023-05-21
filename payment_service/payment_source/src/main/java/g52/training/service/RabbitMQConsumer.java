package g52.training.service;

import com.rabbitmq.client.Channel;
import g52.training.entity.Payment;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.mapper.PaymentHistoryMapper;
import g52.training.mapper.PaymentMapper;
import g52.training.model.booking.BookingPublishedModel;
import g52.training.repository.AccountJpaRepository;
import g52.training.repository.PaymentHistoryJpaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQConsumer {

    @Autowired
    PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @RabbitListener(queues = "${microservice_training.rabbitmq.queue}")
//    @RabbitListener(queues = "Payment_DLQ_DEMO")
    public void recievedMessage(BookingPublishedModel model,
                                @Header(AmqpHeaders.CHANNEL) Channel channel,
//                                @Header(name = "x-death", required = false) Map<?, ?> death,
//                                @Header(AmqpHeaders.MESSAGE_ID) String messageId,
                                @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        Payment payment = PaymentMapper.convertBookingPublishedToPayment(model);
        if (payment.isInitPaymentStatus()){
            paymentHistoryJpaRepository.save(PaymentHistoryMapper.convertPayment(payment));
        }
    }
}
