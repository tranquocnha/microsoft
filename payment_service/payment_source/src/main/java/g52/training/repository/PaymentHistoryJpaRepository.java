package g52.training.repository;

import g52.training.entity.PaymentHistoryEntity;
import g52.training.valueobject.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistoryEntity, Long> {
    List<PaymentHistoryEntity> findAllByAccount(String account);

    List<PaymentHistoryEntity> findAllByStatusAndRetryLessThan(PaymentStatus status, long retry);
}
