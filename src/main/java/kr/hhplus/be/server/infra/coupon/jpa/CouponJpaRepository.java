package kr.hhplus.be.server.infra.coupon.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    Optional<CouponEntity> findById(Long couponId);
}
