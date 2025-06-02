package kr.hhplus.be.server.infra.message.producer;

import kr.hhplus.be.server.application.event.message.OrderCompletedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCompletedProducer {
    private final KafkaTemplate<String, OrderCompletedMessage> kafkaTemplate;

    public void send(OrderCompletedMessage message) {
        kafkaTemplate.send("order.completed", message);
    }
}

