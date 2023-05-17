package g52.training.service;

import com.rabbitmq.client.Channel;
import g52.training.entity.AccountEntity;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.event.PaymentBookingEvent;
import g52.training.repository.PaymentHistoryJpaRepository;
import g52.training.repository.AccountJpaRepository;
import g52.training.valueobject.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class RabbitMQConsumer {

    @Autowired
    AccountJpaRepository paymentJpaRepository;

    @Autowired
    PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @RabbitListener(queues = "${microservice_training.rabbitmq.queue}")
    public void recievedMessage(PaymentBookingEvent event, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("Recieved PaymentBookingEvent From RabbitMQ: " + event.getTraceId());

        PaymentHistoryEntity paymentHistoryEntity = new PaymentHistoryEntity();
//        Optional<AccountEntity> paymentEntityOptional = paymentJpaRepository.findByUserId(event.getUserId());
//        if (paymentEntityOptional.isPresent()) {
//            paymentHistoryEntity.setPaymentId(paymentEntityOptional.get().getId());
//            paymentHistoryEntity.setBookingId(event.getBookingId());
//            paymentHistoryEntity.setTraceId(event.getTraceId());
//            paymentHistoryEntity.setAmount(event.getAmount());
//            paymentHistoryEntity.setCreatedAt(ZonedDateTime.now());
//            paymentHistoryEntity.setStatus(PaymentStatus.COMPLETED);
//            paymentEntityOptional.get().setAmount(paymentEntityOptional.get().getAmount().subtract(event.getAmount()));
//        } else {
//            paymentHistoryEntity.setPaymentId(paymentEntityOptional.get().getId());
//            paymentHistoryEntity.setBookingId(event.getBookingId());
//            paymentHistoryEntity.setTraceId(event.getTraceId());
//            paymentHistoryEntity.setAmount(event.getAmount());
//            paymentHistoryEntity.setCreatedAt(ZonedDateTime.now());
//            paymentHistoryEntity.setStatus(PaymentStatus.FAILED);
//        }
//        paymentHistoryJpaRepository.save(paymentHistoryEntity);
//        paymentJpaRepository.save(paymentEntityOptional.get());

        channel.basicAck(tag, false);
    }
}
