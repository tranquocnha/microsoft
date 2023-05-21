package g52.training.model.booking;

import g52.training.valueobject.PaymentStatus;

public class BookingPublishedModel {
    private String id;
    private AccountPublishedModel account;
    private PricePublishedModel price;
    private TimePublishedModel time;
    private PaymentStatus paymentStatus;

    public BookingPublishedModel() {
    }

    public BookingPublishedModel(String id, AccountPublishedModel account, PricePublishedModel price, TimePublishedModel time, PaymentStatus paymentStatus) {
        this.id = id;
        this.account = account;
        this.price = price;
        this.time = time;
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id;
    }

    public AccountPublishedModel getAccount() {
        return account;
    }

    public PricePublishedModel getPrice() {
        return price;
    }

    public TimePublishedModel getTime() {
        return time;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "BookingPublishedModel{" +
                "id='" + id + '\'' +
                ", account=" + account +
                ", price=" + price +
                ", time=" + time +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
