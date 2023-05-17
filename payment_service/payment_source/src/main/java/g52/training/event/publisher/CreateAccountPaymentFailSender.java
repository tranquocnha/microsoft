package g52.training.event.publisher;


import g52.training.event.CreateAccountPaymentSuccessEvent;
import g52.training.service.RabbitMQSender;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountPaymentFailSender extends RabbitMQSender<CreateAccountPaymentSuccessEvent> {

}
