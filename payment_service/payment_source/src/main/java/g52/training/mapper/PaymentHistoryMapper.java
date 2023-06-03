package g52.training.mapper;

import g52.training.dto.createpay.CreatePayReqDto;
import g52.training.dto.createpay.CreatePayResponseDto;
import g52.training.dto.history.History;
import g52.training.entity.AccountEntity;
import g52.training.entity.Payment;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.valueobject.PaymentsHistoryOperator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class PaymentHistoryMapper {

    public static PaymentHistoryEntity convertCreatePayResponseDto(CreatePayReqDto reqDto, CreatePayResponseDto responseDto, AccountEntity accountEntity, PaymentsHistoryOperator operator) {
        return PaymentHistoryEntity.builder()
                .account(accountEntity.getAccount())
                .bookingId(reqDto.getBookingId())
                .requestId(reqDto.getBookingId())
                .price(reqDto.getPrice())
                .status(responseDto.getStatus())
                .retry(0L)
                .operator(operator)
                .message(responseDto.getMessage())
                .createdAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(responseDto.getCreatedAt()), ZoneId.systemDefault()))
                .build();
    }

    public static PaymentHistoryEntity convertPayment(Payment payment) {
        return PaymentHistoryEntity.builder()
                .account(payment.getEmail())
                .bookingId(payment.getRequestId())
                .requestId(payment.getRequestId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .operator(PaymentsHistoryOperator.PAY)
                .retry(0L)
                .message("")
                .build();
    }

    public static History convertCreatePayResponseDto(PaymentHistoryEntity entity) {
        return new History(entity.getBookingId(), entity.getRequestId(), entity.getPrice(), entity.getStatus(), entity.getOperator(), entity.getMessage());
    }

}
