package kr.hhplus.be.server.domain.payment;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PaymentMessage {
    public static final String CANCEL_FAILED = "현재 상태에서는 결제를 취소할 수 없습니다.";
}