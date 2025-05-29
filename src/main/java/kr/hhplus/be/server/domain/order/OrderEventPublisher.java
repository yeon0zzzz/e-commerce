package kr.hhplus.be.server.domain.order;

public interface OrderEventPublisher {
    void publishCompleted(Order order);
}
