package kr.hhplus.be.server.application.order;


import kr.hhplus.be.server.application.event.OrderEvent;
import kr.hhplus.be.server.application.event.message.OrderCompletedMessage;
import kr.hhplus.be.server.domain.product.stats.event.ProductSalesDailyEvent;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;

import kr.hhplus.be.server.infra.message.producer.OrderCompletedProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final StockService stockService;
    private final UserCouponService userCouponService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderCompletedProducer orderCompletedProducer;

    @Transactional
    public Order orderPayment(OrderPaymentCommand command) {

        // 상품 목록 인스턴스 생성
        List<OrderItem> items = command.items()
                .stream()
                .map(item -> OrderItem.create(item.productId(), item.quantity(), item.price()))
                .toList();

        // 1. 재고 차감
        stockService.deductAll(items);

        // 2. 쿠폰 사용
        userCouponService.use(command.userCouponId());

        // 3. 주문 생성
        Order order = orderService.create(command.userId(), items, command.discountAmount());

        // 4. 결제 처리
        paymentService.pay(order.orderId(), order.finalAmount());

        // 5. 일별 판매량 Redis Cache 저장
        eventPublisher.publishEvent(new ProductSalesDailyEvent(items));

        // 6. 주문 성공 이벤트 발행
        eventPublisher.publishEvent(new OrderEvent.Completed(order));

        // 7. kafka message publish
        orderCompletedProducer.send(OrderCompletedMessage.from(order, items));

        return order;
    }
}
