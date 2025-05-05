package kr.hhplus.be.server.infra.popular.jpa;

import java.math.BigDecimal;

public interface NativePopularProduct {
    Long getProductId();
    String getName();
    BigDecimal getPrice();
    Long getTotalQuantity();
}
