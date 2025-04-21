package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Payment(
        Long paymentId,
        Long orderId,
        PaymentMethod method,
        BigDecimal paidAmount,
        LocalDateTime paidAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public void validateAmount(BigDecimal expected) {
        if (!this.paidAmount.equals(expected)) {
            throw new IllegalArgumentException(PaymentMessage.MISMATCH_AMOUNT);
        }
    }
}
