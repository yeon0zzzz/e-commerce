package kr.hhplus.be.server.infra.message.consumer;

import kr.hhplus.be.server.application.event.message.OrderCompletedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCompletedConsumer {

    @KafkaListener(topics = "order.completed", groupId = "order-payment")
    public void handle(OrderCompletedMessage message) {
        log.info("[Kafka] 주문 완료 메시지 수신: orderId={}, userId={}",
                message.orderId(), message.userId());
    }
}
