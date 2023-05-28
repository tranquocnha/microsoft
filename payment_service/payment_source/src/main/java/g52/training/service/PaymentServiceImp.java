package g52.training.service;

import g52.training.dto.createpay.CreatePayReqDto;
import g52.training.dto.createpay.CreatePayResponseDto;
import g52.training.dto.deposit.DepositReqDto;
import g52.training.dto.deposit.DepositResponseDto;
import g52.training.dto.history.History;
import g52.training.dto.history.HistoryReqDto;
import g52.training.dto.history.HistoryResponseDto;
import g52.training.dto.viewamount.ViewAmountReqDto;
import g52.training.dto.viewamount.ViewAmountResponseDto;
import g52.training.entity.AccountEntity;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.mapper.PaymentHistoryMapper;
import g52.training.repository.AccountJpaRepository;
import g52.training.repository.PaymentHistoryJpaRepository;
import g52.training.valueobject.PaymentStatus;
import g52.training.valueobject.PaymentsHistoryOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp {

    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    public boolean registPayment(String userName) {
        Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(userName);
        if (optionalAccountEntity.isEmpty()) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccount(userName);
            accountEntity.setAmount(BigDecimal.ZERO);
            accountEntity.setCreatedAt(ZonedDateTime.now());
            accountEntity.setUpdatedAt(accountEntity.getCreatedAt());
            accountJpaRepository.save(accountEntity);
            return true;
        }
        return false;
    }

    public CreatePayResponseDto makePayment(CreatePayReqDto createPayDto) {
        Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(createPayDto.getAccount());
        CreatePayResponseDto responseDto = new CreatePayResponseDto();
        responseDto.setRequestId(createPayDto.getRequestId());
        responseDto.setPrice(createPayDto.getPrice());
        responseDto.setCreatedAt(createPayDto.getCreatedAt());
        if (optionalAccountEntity.isEmpty()) {
            throw new IllegalArgumentException("user_id is not exist or not created!");
        } else if (optionalAccountEntity.get().getAmount().compareTo(createPayDto.getPrice()) < 1) {
            responseDto.setStatus(PaymentStatus.FAILED);
            responseDto.setMessage("Amount of user is not enough for payment please deposit!");
        } else {
            responseDto.setStatus(PaymentStatus.SUCCESS);
            responseDto.setMessage("Pay successfully!");
        }
        if (PaymentStatus.SUCCESS.equals(responseDto.getStatus())){
            BigDecimal newAmount = optionalAccountEntity.get().getAmount().subtract(createPayDto.getPrice());
            optionalAccountEntity.get().setAmount(newAmount);
            accountJpaRepository.save(optionalAccountEntity.get());
        }
        paymentHistoryJpaRepository.save(PaymentHistoryMapper.convertCreatePayResponseDto(createPayDto, responseDto, optionalAccountEntity.get(), PaymentsHistoryOperator.PAY));

        return responseDto;
    }

    public void deposit(String account, DepositReqDto reqDto) {
        Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(account);

        CreatePayReqDto createPayReqDto = new CreatePayReqDto();
        createPayReqDto.setPrice(reqDto.getPrice());
        createPayReqDto.setRequestId(UUID.randomUUID().toString());

        CreatePayResponseDto payResponseDto = new CreatePayResponseDto();
        payResponseDto.setRequestId(createPayReqDto.getRequestId());
        payResponseDto.setCreatedAt(ZonedDateTime.now().toEpochSecond());
        if (optionalAccountEntity.isEmpty()) {
            throw new IllegalArgumentException("account is not exist or not created!");
        } else if (reqDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("price can not negative!");
        } else {
            payResponseDto.setStatus(PaymentStatus.SUCCESS);
            payResponseDto.setMessage("Deposit successfully!");
        }
        payResponseDto.setPrice(reqDto.getPrice());
        BigDecimal newAmount = optionalAccountEntity.get().getAmount().add(reqDto.getPrice());
        optionalAccountEntity.get().setAmount(newAmount);
        accountJpaRepository.save(optionalAccountEntity.get());
        paymentHistoryJpaRepository.save(PaymentHistoryMapper.convertCreatePayResponseDto(createPayReqDto, payResponseDto, optionalAccountEntity.get(), PaymentsHistoryOperator.DEPOSIT));
    }

    public HistoryResponseDto getHistory(String account) {
        Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(account);
        if (optionalAccountEntity.isEmpty()){
            throw new IllegalArgumentException("account is not exist or not created!");
        }
        List<History> list =
                paymentHistoryJpaRepository.findAllByAccount(account).stream().map(PaymentHistoryMapper::convertCreatePayResponseDto).collect(Collectors.toList());
        HistoryResponseDto historyResponseDto = new HistoryResponseDto();
        historyResponseDto.setHistories(list);

        ViewAmountResponseDto viewAmountResponseDto = viewAmount(account);
        historyResponseDto.setViewAccount(viewAmountResponseDto);
        return historyResponseDto;
    }

    public ViewAmountResponseDto viewAmount(String account) {
        ViewAmountResponseDto viewAmountResponseDto = new ViewAmountResponseDto();
        Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(account);
        if (optionalAccountEntity.isEmpty()){
            throw new IllegalArgumentException("account is not exist or not created!");
        }
        viewAmountResponseDto.setAccount(optionalAccountEntity.get().getAccount());
        viewAmountResponseDto.setAmount(optionalAccountEntity.get().getAmount());
        return viewAmountResponseDto;
    }
}
