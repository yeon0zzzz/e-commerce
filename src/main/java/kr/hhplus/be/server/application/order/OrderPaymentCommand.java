package kr.hhplus.be.server.application.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;


@Builder
public record OrderPaymentCommand(
        Long userId,
        List<OrderProductCommand> items,
        Long userCouponId,
        BigDecimal discountAmount,
        PaymentMethod paymentMethod
) {}
