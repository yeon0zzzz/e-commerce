package kr.hhplus.be.server.domain.product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long productId);

    Product save(Product product);

    List<Product> findAll();

    List<Product> findTop5PopularProducts();  // 최근 3일 기준
}
