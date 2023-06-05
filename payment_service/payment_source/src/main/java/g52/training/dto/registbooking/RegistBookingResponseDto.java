package g52.training.dto.registbooking;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import g52.training.valueobject.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = RegistBookingResponseDto.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistBookingResponseDto {
    private String bookingId;
    private BigDecimal price;
    private PaymentStatus status;
    private String account;
    private BigDecimal amount;
}
