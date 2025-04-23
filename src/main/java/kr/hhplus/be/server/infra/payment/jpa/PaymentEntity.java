package kr.hhplus.be.server.infra.payment.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.payment.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public static PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .paymentId(payment.paymentId())
                .orderId(payment.orderId())
                .paidAmount(payment.paidAmount())
                .paidAt(payment.paidAt())
                .build();
    }

    public static Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .paymentId(entity.getPaymentId())
                .orderId(entity.getOrderId())
                .paidAmount(entity.getPaidAmount())
                .paidAt(entity.getPaidAt())
                .build();
    }

}

