package kr.hhplus.be.server.infra.product.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findById(Long productId);

    List<ProductEntity> findAll();

//    List<ProductEntity> findTop5PopularProducts();
}
