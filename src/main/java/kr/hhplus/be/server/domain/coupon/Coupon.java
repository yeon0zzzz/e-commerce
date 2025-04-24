package kr.hhplus.be.server.domain.coupon;


import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
public record Coupon(
        Long couponId,
        String name,
        BigDecimal discountAmount,
        long issuedQuantity,
        long issuedCount,
        CouponStatus couponStatus,
        LocalDateTime activatedAt,
        LocalDateTime expiredAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public void validateActive() {
        if (!couponStatus.equals(CouponStatus.ACTIVE)) {
            throw new IllegalArgumentException(CouponMessage.INVALID_COUPON);
        }
    }

    public void validateIssueCount() {
        if (issuedCount >= issuedQuantity) {
            throw new IllegalArgumentException(CouponMessage.EXCEEDED_LIMIT_COUPON);
        }
    }

    public void validateExpired(LocalDateTime now) {
        if (now.isBefore(activatedAt) || now.isAfter(expiredAt)) {
            throw new IllegalArgumentException(CouponMessage.NOT_IN_PERIOD);
        }
    }

    public Coupon issue() {

        validateActive();
        validateIssueCount();

        return Coupon.builder()
                .couponId(this.couponId)
                .name(this.name)
                .discountAmount(this.discountAmount)
                .issuedQuantity(this.issuedQuantity)
                .issuedCount(this.issuedCount + 1)
                .couponStatus(this.couponStatus)
                .activatedAt(this.activatedAt)
                .expiredAt(this.expiredAt)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
