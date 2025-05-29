package kr.hhplus.be.server.infra.message.consumer;

import kr.hhplus.be.server.application.event.message.UserCouponIssuedMessage;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCouponIssuedConsumer {

    private final UserCouponService userCouponService;

    @KafkaListener(topics = "usercoupon.issued", groupId = "usercoupon-group")
    public void consume(UserCouponIssuedMessage message) {
        log.info("[Kafka] 사용자 쿠폰 발급 메시지 수신: userId={}, couponId={}", message.getUserId(), message.getCouponId());
        try {
            UserCoupon userCoupon = UserCoupon.create(message.getUserId(), message.getCouponId());
            userCouponService.save(userCoupon);
            log.info("[Kafka] 사용자 쿠폰 저장 완료: {}", userCoupon);
        } catch (Exception e) {
            log.error("[Kafka] 사용자 쿠폰 저장 실패: {}", e.getMessage(), e);
            // Dead Letter Topic 또는 재시도 정책 적용 대상
        }
    }
}
