package g52.training.event;


import g52.training.entity.PaymentHistoryEntity;
public class PaymentFailedEvent {
    private final PaymentHistoryEntity paymentHistoryEntity;

    public PaymentFailedEvent(PaymentHistoryEntity paymentHistoryEntity) {
        this.paymentHistoryEntity = new PaymentHistoryEntity();
    }
}
