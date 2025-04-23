package kr.hhplus.be.server.domain.product;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class ProductMessage {

    public static final String INACTIVE_PRODUCT = "비활성 상품은 조회할 수 없습니다.";
}