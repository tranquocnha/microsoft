package g52.training.dto.deposit;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import g52.training.dto.history.HistoryResponseDto;
import g52.training.dto.viewamount.ViewAmountResponseDto;
import lombok.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = DepositResponseDto.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DepositResponseDto {
    private ViewAmountResponseDto viewAmount;
    private HistoryResponseDto histories;
}
