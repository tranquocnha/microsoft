package g52.training.model.booking;

import java.math.BigDecimal;

public class PricePublishedModel {
    private BigDecimal price;

    public PricePublishedModel() {
    }

    public PricePublishedModel(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "PricePublishedModel{" +
                "price=" + price +
                '}';
    }
}
