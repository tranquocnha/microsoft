package g52.training.controller;


import g52.training.dto.createpay.CreatePayReqDto;
import g52.training.dto.createpay.CreatePayResponseDto;
import g52.training.dto.deposit.DepositReqDto;
import g52.training.dto.deposit.DepositResponseDto;
import g52.training.dto.history.HistoryReqDto;
import g52.training.dto.history.HistoryResponseDto;
import g52.training.dto.viewamount.ViewAmountReqDto;
import g52.training.dto.viewamount.ViewAmountResponseDto;
import g52.training.service.PaymentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/payment")
@RequiredArgsConstructor
public class PayController {

    private final PaymentServiceImp paymentServiceImp;

    @PostMapping
    public CreatePayResponseDto makePayment(@RequestBody CreatePayReqDto createPayDto) {
        return paymentServiceImp.makePayment(createPayDto);
    }

    @PostMapping(value = "/deposit")
    public DepositResponseDto deposit(@RequestBody DepositReqDto depositReqDto) {
        return paymentServiceImp.deposit(depositReqDto);
    }

    @GetMapping(value = "/histories")
    public HistoryResponseDto getHistory(@RequestBody HistoryReqDto historyReqDto) {
        return paymentServiceImp.getHistory(historyReqDto);
    }

    @GetMapping(value = "/view-amount")
    public ViewAmountResponseDto viewAmount(@RequestBody ViewAmountReqDto viewAmountReqDto) {
        return paymentServiceImp.viewAmount(viewAmountReqDto);
    }

    @ExceptionHandler
    protected ResponseEntity<String> handleEntityNotFound(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
