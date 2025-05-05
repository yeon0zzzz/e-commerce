package kr.hhplus.be.server.domain.product;

import java.util.List;

public interface ProductRepository {
    Product findById(Long productId);

    Product save(Product product);

    List<Product> findAll();
}
