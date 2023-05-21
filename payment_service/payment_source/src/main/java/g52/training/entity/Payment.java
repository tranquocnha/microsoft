package g52.training.entity;


import g52.training.valueobject.PaymentStatus;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Payment extends AggregateRoot<String> {
    private final String email;
    private final BigDecimal price;
    private final String requestId;
    private PaymentStatus status;
    private List<String> messages;

    public Payment(String email, BigDecimal price, String requestId, PaymentStatus status) {
        this.email = email;
        this.price = price;
        this.requestId = requestId;
        this.status = status;
    }
    public String getEmail() {
        return email;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getRequestId() {
        return requestId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void initPayment(){
        validatePayment();
        status = PaymentStatus.WAIT;
    }

    public void payPaymentSuccess(){
        if (status != PaymentStatus.WAIT) {
            throw new IllegalArgumentException("payPaymentSuccess is fail cause status != PaymentStatus.WAIT " + this);
        }
        status = PaymentStatus.SUCCESS;
    }

    public boolean isInitPaymentStatus(){
        if (status != PaymentStatus.WAIT) {
            throw new IllegalArgumentException("payPaymentSuccess is fail cause status != PaymentStatus.WAIT " + this);
        }
        return true;
    }

    public void payPaymentFail(){
        if (status != PaymentStatus.WAIT) {
            throw new IllegalArgumentException("payPaymentSuccess is fail cause status != PaymentStatus.WAIT " + this);
        }
        status = PaymentStatus.FAILED;
    }

    public void validatePayment(){
        if (null == email || null == price || null == requestId || status == null) {
            throw new IllegalArgumentException("validatePayment is false with exist field null" + this);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("validatePayment is false with price < 0" + this);
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "email='" + email + '\'' +
                ", price=" + price +
                ", requestId='" + requestId + '\'' +
                ", status=" + status +
                ", messages=" + messages +
                '}';
    }
}
