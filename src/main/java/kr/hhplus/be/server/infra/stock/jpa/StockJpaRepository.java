package kr.hhplus.be.server.infra.stock.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {

    StockEntity findByProductId(Long productId);
}
