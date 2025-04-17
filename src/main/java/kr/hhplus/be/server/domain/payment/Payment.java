package kr.hhplus.be.server.domain.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Payment(
        Long paymentId,
        Long orderId,
        PaymentStatus status,
        PaymentMethod method,
        BigDecimal paidAmount,
        LocalDateTime paidAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Payment complete(BigDecimal expectedAmount) {
        if (!this.paidAmount.equals(expectedAmount)) {
            throw new IllegalArgumentException(PaymentMessage.MISMATCH_AMOUNT);
        }

        return Payment.builder()
                .paymentId(this.paymentId)
                .orderId(this.orderId)
                .status(PaymentStatus.SUCCESS)
                .method(this.method)
                .paidAmount(this.paidAmount)
                .paidAt(LocalDateTime.now())
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void validatePayable() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException(PaymentMessage.NOT_PAYABLE);
        }
    }
}
