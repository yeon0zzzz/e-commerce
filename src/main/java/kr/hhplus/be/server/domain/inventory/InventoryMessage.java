package kr.hhplus.be.server.domain.inventory;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class InventoryMessage {
    public static final String STOCK_NOT_ENOUGH = "재고가 부족합니다.";
    public static final String INVENTORY_NOT_FOUND = "재고 정보가 존재하지 않습니다.";
}