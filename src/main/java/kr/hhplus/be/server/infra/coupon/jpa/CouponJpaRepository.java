package kr.hhplus.be.server.infra.coupon.jpa;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CouponEntity c where c.couponId = :couponId")
    Optional<CouponEntity> findByCouponId(Long couponId);
}
