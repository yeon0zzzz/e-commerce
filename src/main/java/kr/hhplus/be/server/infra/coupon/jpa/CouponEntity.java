package kr.hhplus.be.server.infra.coupon.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        CouponEntity entity = new CouponEntity();
        entity.couponId = coupon.couponId();
        entity.name = coupon.name();
        entity.discountAmount = coupon.discountAmount();
        entity.issuedQuantity = coupon.issuedQuantity();
        entity.issuedCount = coupon.issuedCount();
        entity.couponStatus = coupon.couponStatus();
        entity.activatedAt = coupon.activatedAt();
        entity.expiredAt = coupon.expiredAt();
        entity.createdAt = coupon.createdAt();
        entity.updatedAt = coupon.updatedAt();

        return entity;
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