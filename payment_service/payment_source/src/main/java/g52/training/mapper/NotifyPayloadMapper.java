package g52.training.mapper;

import com.fpt.g52.common_service.notification.model.shared.enums.PaymentStatus;
import com.fpt.g52.common_service.notification.model.shared.enums.Sender;
import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;
import g52.training.entity.PaymentHistoryEntity;

public final class NotifyPayloadMapper {
    public static OrderPayload convertToOrderPayload(PaymentHistoryEntity paymentHistoryEntity) {
        OrderPayload orderPayload = new OrderPayload();
        orderPayload.setBookingId(paymentHistoryEntity.getBookingId());
        orderPayload.setPrice(paymentHistoryEntity.getPrice());
        orderPayload.setPaymentStatus(convertStatus(paymentHistoryEntity.getStatus()));
        orderPayload.setSender(Sender.PAYMENT_SERVICE);
        orderPayload.setReceiver("NOTIFY_SERVICE");
        return orderPayload;
    }

    private static PaymentStatus convertStatus(g52.training.valueobject.PaymentStatus paymentStatus) {
        switch (paymentStatus){
            case SUCCESS : return PaymentStatus.SUCCESS;
            case FAILED: return PaymentStatus.FAILURE;
            case WAIT:return PaymentStatus.WAITING;
            default: return PaymentStatus.UNKNOWN;
        }
    }
}
