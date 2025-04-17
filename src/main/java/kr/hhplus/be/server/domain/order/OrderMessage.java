package kr.hhplus.be.server.domain.order;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class OrderMessage {
    public static final String NOT_PAYABLE_STATUS = "결제할 수 없는 상태입니다.";
}