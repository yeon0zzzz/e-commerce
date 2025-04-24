package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Payment(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime paidAt
) {

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, CANCELLED;

        public boolean isCancellable() {
            return this == PENDING || this == COMPLETED;
        }
    }

    public void validateCancellable() {
        if (!status.isCancellable()) {
            throw new IllegalStateException(PaymentMessage.CANCEL_FAILED);
        }
    }

    public static Payment complete(Long orderId, BigDecimal amount) {
        return Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.COMPLETED)
                .paidAt(LocalDateTime.now())
                .build();
    }

    public Payment cancel() {

        validateCancellable();

        return Payment.builder()
                .paymentId(paymentId)
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.CANCELLED)
                .paidAt(paidAt)
                .build();
    }

    public static Payment fail(Long orderId, BigDecimal amount) {
        return Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.FAILED)
                .paidAt(LocalDateTime.now())
                .build();
    }
}
