package kr.hhplus.be.server.infra.coupon.usercoupon.jpa;


import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class UserCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Long userCouponId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "used")
    private boolean used;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static UserCouponEntity toEntity(UserCoupon userCoupon) {
        UserCouponEntity entity = new UserCouponEntity();
        entity.userCouponId = userCoupon.userCouponId();
        entity.userId = userCoupon.userId();
        entity.couponId = userCoupon.couponId();
        entity.used = userCoupon.used();
        entity.usedAt = userCoupon.usedAt();
        entity.createdAt = userCoupon.createdAt();
        return entity;
    }

    public static UserCoupon toDomain(UserCouponEntity entity) {
        return UserCoupon.builder()
                .userCouponId(entity.getUserCouponId())
                .userId(entity.getUserId())
                .couponId(entity.getCouponId())
                .used(entity.isUsed())
                .usedAt(entity.getUsedAt())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
