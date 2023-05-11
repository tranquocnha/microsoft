package g52.training.service;

import g52.training.dto.Employee;
import g52.training.entity.PaymentEntity;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.event.PaymentBookingEvent;
import g52.training.repository.PaymentHistoryJpaRepository;
import g52.training.repository.PaymentJpaRepository;
import g52.training.valueobject.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class RabbitMQConsumer {

    @Autowired
    PaymentJpaRepository paymentJpaRepository;

    @Autowired
    PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @RabbitListener(queues = "${microservice_training.rabbitmq.queue}")
    public void recievedMessage(PaymentBookingEvent event) {
        System.out.println("Recieved PaymentBookingEvent From RabbitMQ: " + event.getTraceId());

        PaymentHistoryEntity paymentHistoryEntity = new PaymentHistoryEntity();
        Optional<PaymentEntity> paymentEntityOptional = paymentJpaRepository.findByUserId(event.getUserId());
        if (paymentEntityOptional.isPresent()) {
            paymentHistoryEntity.setPaymentId(paymentEntityOptional.get().getId());
            paymentHistoryEntity.setBookingId(event.getBookingId());
            paymentHistoryEntity.setTraceId(event.getTraceId());
            paymentHistoryEntity.setAmount(event.getAmount());
            paymentHistoryEntity.setCreatedAt(ZonedDateTime.now());
            paymentHistoryEntity.setStatus(PaymentStatus.COMPLETED);
            paymentEntityOptional.get().setAmount(paymentEntityOptional.get().getAmount().subtract(event.getAmount()));
        } else {
            paymentHistoryEntity.setPaymentId(paymentEntityOptional.get().getId());
            paymentHistoryEntity.setBookingId(event.getBookingId());
            paymentHistoryEntity.setTraceId(event.getTraceId());
            paymentHistoryEntity.setAmount(event.getAmount());
            paymentHistoryEntity.setCreatedAt(ZonedDateTime.now());
            paymentHistoryEntity.setStatus(PaymentStatus.FAILED);
        }
        paymentHistoryJpaRepository.save(paymentHistoryEntity);
        paymentJpaRepository.save(paymentEntityOptional.get());

    }
}
