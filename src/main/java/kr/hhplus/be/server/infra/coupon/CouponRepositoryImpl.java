package kr.hhplus.be.server.infra.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.infra.coupon.jpa.CouponEntity;
import kr.hhplus.be.server.infra.coupon.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon findByCouponId(Long couponId) {
        return couponJpaRepository.findByCouponId(couponId)
                .map(CouponEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다."));
    }

    @Override
    public void save(Coupon coupon) {
        CouponEntity entity = CouponEntity.toEntity(coupon);
        couponJpaRepository.save(entity);
    }
}
