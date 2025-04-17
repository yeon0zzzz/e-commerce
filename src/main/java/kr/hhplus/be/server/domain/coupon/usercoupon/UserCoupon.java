package kr.hhplus.be.server.domain.coupon.usercoupon;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserCoupon(
        Long userCouponId,
        Long userId,
        Long couponId,
        boolean used,
        LocalDateTime usedAt,
        LocalDateTime createdAt
) {

    public void validateUsable() {
        if (this.used) {
            throw new IllegalArgumentException(UserCouponMessage.USED_COUPON);
        }
    }

    public UserCoupon use() {

        validateUsable();

        return UserCoupon.builder()
                .userCouponId(this.userCouponId)
                .userId(this.userId)
                .couponId(this.couponId)
                .used(true)
                .usedAt(LocalDateTime.now())
                .createdAt(this.createdAt)
                .build();
    }
}
