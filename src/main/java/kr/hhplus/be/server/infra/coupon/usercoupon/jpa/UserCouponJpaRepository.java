package kr.hhplus.be.server.infra.coupon.usercoupon.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponJpaRepository extends JpaRepository<UserCouponEntity, Long> {
    Optional<UserCouponEntity> findById(Long userCouponId);
    List<UserCouponEntity> findAllByUserIdAndUsedIsFalse(Long userId);
}
