package g52.training.dto.history;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import g52.training.dto.viewamount.ViewAmountResponseDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = HistoryResponseDto.class)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HistoryResponseDto {
    private ViewAmountResponseDto viewAccount;
    private List<History> histories;
}
