package kr.hhplus.be.server.domain.stock;

public interface StockRepository {
    Stock findByProductId(Long productId);

    Stock save(Stock stock);
}
