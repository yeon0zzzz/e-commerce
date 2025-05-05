package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponDto {

    @Builder
    public record Response(
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
        public static Response of(Coupon coupon) {
            return Response.builder()
                    .couponId(coupon.couponId())
                    .name(coupon.name())
                    .discountAmount(coupon.discountAmount())
                    .issuedQuantity(coupon.issuedQuantity())
                    .issuedCount(coupon.issuedCount())
                    .couponStatus(coupon.couponStatus())
                    .activatedAt(coupon.activatedAt())
                    .expiredAt(coupon.expiredAt())
                    .createdAt(coupon.createdAt())
                    .updatedAt(coupon.updatedAt())
                    .build();
        }
    }

}
