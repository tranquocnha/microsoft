package g52.training.mapper;

import g52.training.dto.createpay.CreatePayReqDto;
import g52.training.dto.createpay.CreatePayResponseDto;
import g52.training.dto.history.History;
import g52.training.entity.AccountEntity;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.valueobject.PaymentsHistoryOperator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class PaymentHistoryMapper {

    public PaymentHistoryEntity convertCreatePayResponseDto(CreatePayReqDto reqDto, CreatePayResponseDto responseDto, AccountEntity accountEntity, PaymentsHistoryOperator operator) {
        return PaymentHistoryEntity.builder()
                .accountId(accountEntity.getId())
                .bookingId(reqDto.getBookingId())
                .requestId(reqDto.getRequestId())
                .price(reqDto.getPrice())
                .status(responseDto.getStatus())
                .operator(operator)
                .message(responseDto.getMessage())
                .createdAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(responseDto.getCreatedAt()), ZoneId.systemDefault()))
                .build();
    }

    public History convertCreatePayResponseDto(PaymentHistoryEntity entity) {
        return new History(entity.getBookingId(), entity.getRequestId(), entity.getPrice(), entity.getStatus(), entity.getOperator(), entity.getMessage());
    }

}
