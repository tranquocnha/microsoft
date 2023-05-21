package g52.training.event;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class CreateBookingPaymentEvent {

    private final String id;
    private final String account;
    private final BigDecimal price;
    private final Long createdAt;

    public CreateBookingPaymentEvent(String id, String account, BigDecimal price, Long createdAt) {
        this.id = id;
        this.account = account;
        this.price = price;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getCreatedAt() {
        return createdAt;
    }
}
