package g52.training.event;

import g52.training.entity.PaymentHistoryEntity;

public class PaymentCompletedEvent {

    private final PaymentHistoryEntity paymentHistoryEntity;

    public PaymentCompletedEvent(PaymentHistoryEntity paymentHistoryEntity) {
        this.paymentHistoryEntity = paymentHistoryEntity;
    }

}
