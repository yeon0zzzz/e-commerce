package kr.hhplus.be.server.domain.payment;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PaymentMessage {
    public static final String MISMATCH_AMOUNT = "결제 금액이 일치하지 않습니다.";
}