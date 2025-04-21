package kr.hhplus.be.server.infra.coupon.jpa;

import kr.hhplus.be.server.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    Optional<CouponEntity> findByCouponId(Long couponId);
}
