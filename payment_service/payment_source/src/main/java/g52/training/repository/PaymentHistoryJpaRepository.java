package g52.training.repository;

import g52.training.entity.PaymentEntity;
import g52.training.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentHistoryJpaRepository extends JpaRepository<PaymentHistoryEntity, Long> {

}
