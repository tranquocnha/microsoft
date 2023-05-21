package g52.training.mapper;

import g52.training.dto.history.History;
import g52.training.entity.Payment;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.model.booking.BookingPublishedModel;

public final class PaymentMapper {
    private PaymentMapper() {
    }

    public static Payment convertBookingPublishedToPayment(BookingPublishedModel published) {
        return new Payment(published.getAccount().getId(), published.getPrice().getPrice(), published.getId(), published.getPaymentStatus());
    }

    public static History convertCreatePayResponseDto(PaymentHistoryEntity entity) {
        return new History(entity.getBookingId(), entity.getRequestId(), entity.getPrice(), entity.getStatus(), entity.getOperator(), entity.getMessage());
    }

}
