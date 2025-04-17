package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderCommand(
        Long userId,
        List<OrderProductCommand> products,
        Long usedPoint,
        Long userCouponId,
        PaymentMethod paymentMethod,
        BigDecimal paidAmount
) {}
