package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import lombok.Builder;

import java.time.LocalDateTime;

public class UserCouponDto {

    public record Request(
            Long userId
    ) {
    }

    @Builder
    public record Response(
            Long userCouponId,
            Long userId,
            Long couponId,
            boolean used,
            LocalDateTime usedAt,
            LocalDateTime createdAt
    ) {
        public static UserCouponDto.Response of(UserCoupon userCoupon) {
            return UserCouponDto.Response.builder()
                    .userCouponId(userCoupon.userCouponId())
                    .userId(userCoupon.userId())
                    .couponId(userCoupon.couponId())
                    .used(userCoupon.used())
                    .createdAt(userCoupon.createdAt())
                    .build();
        }
    }
}
