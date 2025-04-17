package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.payment.PaymentMethod;
import lombok.Builder;

import java.util.List;


@Builder
public record OrderPaymentCommand(
        Long userId,
        List<OrderProductCommand> items,
        Long userCouponId,
        PaymentMethod paymentMethod
) {}
