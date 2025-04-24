package kr.hhplus.be.server.infra.stock;

import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockRepository;
import kr.hhplus.be.server.infra.stock.jpa.StockEntity;
import kr.hhplus.be.server.infra.stock.jpa.StockJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @Override
    public Stock findByProductId(Long productId) {
        return StockEntity.toDomain(stockJpaRepository.findByProductId(productId));
    }

    @Override
    public Stock save(Stock stock) {
        return StockEntity.toDomain(
                stockJpaRepository.save(
                        StockEntity.toEntity(stock)
                )
        );
    }
}
