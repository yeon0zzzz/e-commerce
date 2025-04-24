package kr.hhplus.be.server.infra.stock.jpa;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from StockEntity c where c.productId = :productId")
    StockEntity findByProductId(Long productId);
}
