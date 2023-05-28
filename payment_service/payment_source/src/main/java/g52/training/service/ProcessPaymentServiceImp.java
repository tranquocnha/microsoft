package g52.training.service;

import g52.training.dto.createpay.CreatePayResponseDto;
import g52.training.entity.AccountEntity;
import g52.training.entity.PaymentHistoryEntity;
import g52.training.entity.PaymentHistoryScheduleEntity;
import g52.training.mapper.PaymentHistoryMapper;
import g52.training.repository.AccountJpaRepository;
import g52.training.repository.PaymentHistoryJpaRepository;
import g52.training.repository.PaymentHistoryScheduleJpaRepository;
import g52.training.valueobject.PaymentStatus;
import g52.training.valueobject.PaymentsHistoryOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessPaymentServiceImp {

    @Value("${booking.host}")
    private String bookingHost;
    private final PaymentHistoryScheduleJpaRepository paymentHistoryScheduleJpaRepository;
    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final String NOTIFY_PAID_API = "/api/bookings/{id}/paycomplete";
    @Scheduled(fixedRate = 3000,
            initialDelay = 3000)
    synchronized public void processPaymentSchedule() {
        List<PaymentHistoryEntity> list = paymentHistoryJpaRepository.findAllByStatusAndRetryLessThan(PaymentStatus.WAIT, 3);
        List<String> requestIdNotify = new ArrayList<>();
        System.out.println("processPaymentSchedule at " + System.currentTimeMillis() + " list size : " + list.size());
        for (PaymentHistoryEntity e : list) {
            Optional<AccountEntity> optionalAccountEntity = accountJpaRepository.findAccountEntityByAccount(e.getAccount());
            PaymentHistoryScheduleEntity sE = new PaymentHistoryScheduleEntity();
            sE.setPaymentsHistoryId(e.getId());
            if (optionalAccountEntity.isEmpty()) {
                sE.setStatus(PaymentStatus.WAIT);
                sE.setMessage("account is not exist or not created!");
            } else if (optionalAccountEntity.get().getAmount().compareTo(e.getPrice()) < 1) {
                sE.setStatus(PaymentStatus.WAIT);
                sE.setMessage("Amount of user is not enough for payment please deposit!");
            } else {
                sE.setStatus(PaymentStatus.SUCCESS);
                sE.setMessage("Pay successfully!");
            }
            if (PaymentStatus.SUCCESS.equals(sE.getStatus())){
                BigDecimal newAmount = optionalAccountEntity.get().getAmount().subtract(e.getPrice());
                optionalAccountEntity.get().setAmount(newAmount);
                accountJpaRepository.save(optionalAccountEntity.get());
            }
            Long retry = e.getRetry()+1;
            e.setRetry(retry);
            e.setMessage(sE.getMessage());
            if (retry == 3 && !PaymentStatus.SUCCESS.equals(sE.getStatus())) {
                sE.setStatus(PaymentStatus.FAILED);
            }
            e.setStatus(sE.getStatus());

            paymentHistoryJpaRepository.save(e);
            paymentHistoryScheduleJpaRepository.save(sE);
            requestIdNotify.add(e.getBookingId());
        }
//
//        RestTemplate restTemplate2 = new RestTemplate();
//        String fooResourceUrl
//                = bookingHost + "/api/v1/auth/validate-token?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGZwdC5jb20iLCJleHAiOjE2ODQ3MjIyNjksImlhdCI6MTY4NDY4NjI2OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.e47ToQjoRPoQBLV6tDVKUiMpvTs9jpM9IPTSAhHbIVU";
//        ResponseEntity<String> response2
//                = restTemplate2.postForEntity(fooResourceUrl,null, String.class);
//        System.out.println(response2);

        for (String str: requestIdNotify) {
            String bookingNotify = bookingHost + NOTIFY_PAID_API.replace("{id}", str);
            System.out.println("Send notice to bookingNotify");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(bookingNotify, HttpMethod.PUT, null, String.class);
            System.out.println(response);
            System.out.println("End send notice to bookingNotify");
        }
    }
}
