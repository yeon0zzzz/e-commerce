package kr.hhplus.be.server.domain.product;

import java.util.List;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findById(Long productId) {
        Product product = productRepository.findById(productId);
        product.validateIsActive();
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> findPopular() {
        return productRepository.findTop5PopularProducts();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
