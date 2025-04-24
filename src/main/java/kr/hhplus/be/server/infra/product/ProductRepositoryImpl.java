package kr.hhplus.be.server.infra.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.infra.product.jpa.ProductEntity;
import kr.hhplus.be.server.infra.product.jpa.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product findById(Long productId) {
        return productJpaRepository.findById(productId)
                .map(ProductEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));
    }

    @Override
    public Product save(Product product) {
        return ProductEntity.toDomain(
                productJpaRepository.save(
                        ProductEntity.toEntity(product)
                )
        );
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .toList();
    }

    @Override
    public List<Product> findTop5PopularProducts() {
        return List.of();
    }
}
