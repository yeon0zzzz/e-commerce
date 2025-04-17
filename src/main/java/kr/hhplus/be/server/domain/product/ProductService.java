package kr.hhplus.be.server.domain.product;

import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getById(Long productId) {
        Product product = productRepository.findById(productId);
        product.validateIsActive();
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> findPopular(int limit) {
        return productRepository.findPopularProducts(limit);
    }
}
