package g52.training.event;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class PaymentBookingEvent {

    private UUID userId;
    private UUID bookingId;
    private UUID traceId;
    private BigDecimal amount;
    private ZonedDateTime createdAt;

    public PaymentBookingEvent() {
    }

    public PaymentBookingEvent(UUID userId, UUID bookingId, UUID traceId, BigDecimal amount) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.traceId = traceId;
        this.amount = amount;
        this.createdAt = ZonedDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public UUID getTraceId() {
        return traceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
