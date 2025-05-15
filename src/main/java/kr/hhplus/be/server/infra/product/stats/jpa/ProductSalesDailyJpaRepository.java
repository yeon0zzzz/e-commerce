package kr.hhplus.be.server.infra.product.stats.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSalesDailyJpaRepository extends JpaRepository<ProductSalesDailyEntity, Long> {
    Optional<ProductSalesDailyEntity> findById(Long id);
}
