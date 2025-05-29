package kr.hhplus.be.server.infra.message.producer;

import kr.hhplus.be.server.application.event.message.UserCouponIssuedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCouponIssuedProducer {
    private final KafkaTemplate<String, UserCouponIssuedMessage> kafkaTemplate;

    public void send(UserCouponIssuedMessage message) {
        kafkaTemplate.send("usercoupon.issued", String.valueOf(message.getCouponId()), message);
    }
}
