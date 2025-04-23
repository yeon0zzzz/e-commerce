package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Payment(
        Long paymentId,
        Long orderId,
        BigDecimal paidAmount,
        LocalDateTime paidAt
) {
    public void validateAmount(BigDecimal expected) {
        if (!this.paidAmount.equals(expected)) {
            throw new IllegalArgumentException(PaymentMessage.MISMATCH_AMOUNT);
        }
    }
}
