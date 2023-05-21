package g52.training.repository;

import g52.training.entity.PaymentHistoryScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryScheduleJpaRepository extends JpaRepository<PaymentHistoryScheduleEntity, Long> {

}
