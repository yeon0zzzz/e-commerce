package kr.hhplus.be.server.application.order;


import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import kr.hhplus.be.server.domain.inventory.InventoryService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.product.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final InventoryService inventoryService;
    private final UserCouponService userCouponService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Transactional
    public Order orderPayment(OrderPaymentCommand command) {

        // 상품 목록 인스턴스 생성
        List<OrderItem> items = command.items()
                .stream()
                .map(item -> OrderItem.builder()
                        .productId(item.productId())
                        .quantity(item.quantity())
                        .price(item.price())
                        .totalPrice(OrderItem.calculateTotal(item.price(), item.quantity()))
                        .build())
                .toList();

        // 1. 재고 차감 (예약)
        inventoryService.reserveAll(items);

        // 2. 쿠폰 사용
        userCouponService.use(command.userCouponId());

        // 3. 주문 생성
        Order order = orderService.create(command.userId(), items, command.discountAmount());

        // 4. 결제 처리
        paymentService.pay(order.orderId(), order.finalAmount(), command.paymentMethod());

        return order;
    }
}
