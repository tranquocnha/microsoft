package g52.training.entity;


import g52.training.valueobject.PaymentStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments_history_schedule")
@Entity
public class PaymentHistoryScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long paymentsHistoryId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @CreationTimestamp
    private ZonedDateTime createdAt;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentHistoryScheduleEntity that = (PaymentHistoryScheduleEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
