package g52.training.dto.history;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import g52.training.valueobject.PaymentStatus;
import g52.training.valueobject.PaymentsHistoryOperator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class History {
    private String bookingId;
    private String requestId;
    private BigDecimal price;
    private PaymentStatus status;
    private PaymentsHistoryOperator operator;
    private String message;
    private Long createAt;
}
