package kr.hhplus.be.server.application.order;


import kr.hhplus.be.server.domain.inventory.InventoryService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public class OrderFacade {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final PointService pointService;
//    private final CouponService couponService; // TODO
    private final OrderService orderService;
    private final PaymentService paymentService;

    public Order placeOrder(OrderCommand command) {

        // 1. 상품 조회
        // 2. 상품 재고 조회
        // 3. 주문 생성
        // 4. 쿠폰 조회 : TODO
        // 5. 잔액 조회
        // 6. 결제 요청

        // 1. 상품 존재 여부만 확인
        for (OrderProductCommand item : command.products()) {
            productService.getById(item.productId()); // 유효성만 확인
        }

        // 2. 재고 수량 확인 → Inventory 도메인에서 캡슐화된 검증
        for (OrderProductCommand item : command.products()) {
            inventoryService.validateStockEnough(item.productId(), item.quantity());
        }

        // 3. 포인트 사용 (0 이상일 때만)
        if (command.usedPoint() > 0) {
            pointService.use(command.userId(), command.usedPoint());
        }

        // 4. 쿠폰 사용 (TODO)
        // if (command.userCouponId() != null) {
        //     couponService.use(command.userCouponId());
        // }

        // 5. 주문 생성
        List<OrderItem> items = command.products().stream()
                .map(p -> OrderItem.builder()
                        .productId(p.productId())
                        .quantity(p.quantity())
                        .price(p.price())
                        .totalPrice(p.price().multiply(BigDecimal.valueOf(p.quantity())))
                        .build()
                ).toList();

        BigDecimal totalAmount = items.stream()
                .map(OrderItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal finalAmount = totalAmount
                .subtract(discountAmount)
                .subtract(BigDecimal.valueOf(command.usedPoint()));

        Order order = orderService.createOrder(command.userId(), items, discountAmount, command.usedPoint());

        // 6. 결제 처리
        paymentService.requestPayment(order.orderId(), command.paidAmount(), command.paymentMethod());
        paymentService.completePayment(order.orderId(), finalAmount);

        return order;
    }
}
