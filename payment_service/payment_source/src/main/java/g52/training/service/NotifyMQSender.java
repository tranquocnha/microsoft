package g52.training.service;


import com.fpt.g52.common_service.notification.model.shared.constant.NotificationSharedConstant;
import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;
import g52.training.event.publisher.MessageSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotifyMQSender implements MessageSender<OrderPayload>, NotificationSharedConstant {

    @Autowired
    private AmqpTemplate rabbitTemplateA;

    @Override
    public void send(OrderPayload t) {
        rabbitTemplateA.convertAndSend(NOTIFY_TOPIC_EXCHANGE, NOTIFY_ORDER_PAYMENT_ROUTING_KEY, t);
    }
}
