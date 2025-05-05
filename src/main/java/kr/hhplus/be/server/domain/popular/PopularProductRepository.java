package kr.hhplus.be.server.domain.popular;

import java.util.List;

public interface PopularProductRepository {
    List<PopularProduct> findPopularProducts();  // 최근 3일 기준
}
