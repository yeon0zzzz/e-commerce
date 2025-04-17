package kr.hhplus.be.server.application.order;


import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponService;
import kr.hhplus.be.server.domain.inventory.InventoryService;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.order.OrderItem;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.payment.PaymentMethod;
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

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final PointService pointService;
    private final CouponService couponService;
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

        // 1. 상품 재고 조회
        items.forEach(item ->
                inventoryService.validateStockEnough(item.productId(), item.quantity()));
        // 2. 사용자 쿠폰 조회
        UserCoupon userCoupon = userCouponService.findByUserCouponId(command.userCouponId());
        // 3. 쿠폰 조회
        Coupon coupon = couponService.findByCouponId(userCoupon.couponId());
        // 4. 주문 생성 (userId, orderItem, discountAmount)
        Order order = orderService.create(command.userId(), items, coupon.discountAmount());
        // 5. 결제 요청
        paymentService.request(order.orderId(), order.finalAmount(), command.paymentMethod());
        // 6. 상품 재고 점유 (예약)
        inventoryService.reserveAll(items);
        // 7. 쿠폰 사용 처리
        userCouponService.use(command.userCouponId());
        // 8. 결제 성공 처리
        paymentService.complete(order.orderId(), order.finalAmount());

        return order;
    }
}
