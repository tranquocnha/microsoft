package g52.training.controller;


import g52.training.dto.deposit.DepositReqDto;
import g52.training.dto.deposit.DepositResponseDto;
import g52.training.dto.history.HistoryResponseDto;
import g52.training.service.PaymentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/payment")
@RequiredArgsConstructor
public class PayController {

    private final PaymentServiceImp paymentServiceImp;

    @PostMapping(value = "/deposit")
    public ResponseEntity<DepositResponseDto> deposit(@RequestBody DepositReqDto depositReqDto) {
        return ResponseEntity.ok(paymentServiceImp.deposit(depositReqDto));
    }

    @PostMapping(value = "/regist-payment")
    public ResponseEntity<Boolean> registPayment() {
        return ResponseEntity.ok(paymentServiceImp.registPayment(SecurityContextHolder.getContext().getAuthentication().getName()));
    }


    @GetMapping(value = "/histories")
    public ResponseEntity<HistoryResponseDto> getHistory() {
        return ResponseEntity.ok(paymentServiceImp.getHistory(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @ExceptionHandler
    protected ResponseEntity<String> handleEntityNotFound(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
