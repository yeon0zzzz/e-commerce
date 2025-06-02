package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.application.event.message.UserCouponIssuedMessage;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponCacheService;
import kr.hhplus.be.server.infra.message.producer.UserCouponIssuedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponCacheFacade {

    private final UserCouponCacheService userCouponCacheService;
    private final UserCouponIssuedProducer userCouponIssuedProducer;

    public void publish(Long userId, Long couponId) {
        boolean issued = userCouponCacheService.atomicPublish(userId, couponId);

        // kafka 메시지 발행
        userCouponIssuedProducer.send(UserCouponIssuedMessage.from(UserCoupon.create(userId, couponId)));
    }
}