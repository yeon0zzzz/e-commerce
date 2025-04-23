package kr.hhplus.be.server.infra.coupon.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "name")
    private String name;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "issued_quantity")
    private long issuedQuantity;

    @Column(name = "issued_count")
    private long issuedCount;

    @Column(name = "coupon_status")
    private CouponStatus couponStatus;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static CouponEntity toEntity(Coupon coupon) {
        return CouponEntity.builder()
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

    public static Coupon toDomain(CouponEntity entity) {
        return Coupon.builder()
                .couponId(entity.getCouponId())
                .name(entity.getName())
                .discountAmount(entity.getDiscountAmount())
                .issuedQuantity(entity.getIssuedQuantity())
                .issuedCount(entity.getIssuedCount())
                .couponStatus(entity.getCouponStatus())
                .activatedAt(entity.getActivatedAt())
                .expiredAt(entity.getExpiredAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}