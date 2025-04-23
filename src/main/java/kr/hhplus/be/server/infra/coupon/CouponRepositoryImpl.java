package kr.hhplus.be.server.infra.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponRepository;
import kr.hhplus.be.server.infra.coupon.jpa.CouponEntity;
import kr.hhplus.be.server.infra.coupon.jpa.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon findById(Long couponId) {
        return couponJpaRepository.findById(couponId)
                .map(CouponEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다."));
    }

    @Override
    public Coupon save(Coupon coupon) {
        return CouponEntity.toDomain(couponJpaRepository.save(CouponEntity.toEntity(coupon)));
    }
}
