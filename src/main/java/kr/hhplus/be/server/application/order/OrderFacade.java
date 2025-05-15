package kr.hhplus.be.server.application.order;


import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final StockService stockService;
    private final UserCouponService userCouponService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;

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
        eventPublisher.publishEvent(new OrderCompletedEvent(items));

        return order;
    }
}
