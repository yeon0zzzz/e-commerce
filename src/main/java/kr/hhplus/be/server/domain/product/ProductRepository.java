package kr.hhplus.be.server.domain.product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long productId);

    List<Product> findAll();

    List<Product> findPopularProducts(int limit);  // 최근 3일 기준 인기상품 등
}
