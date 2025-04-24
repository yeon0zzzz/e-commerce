package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public Stock findByProductId(Long productId) {
        return stockRepository.findByProductId(productId);
    }

    public void validateStockEnough(Long productId, Long requestedQuantity) {
        Stock stock = stockRepository.findByProductId(productId);
        stock.validateStockEnough(requestedQuantity);
    }

    public void deduct(Long productId, Long requestedQuantity) {
        Stock stock = stockRepository.findByProductId(productId);
        Stock deducted = stock.deduct(requestedQuantity);
        stockRepository.save(deducted);
    }

    public void deductAll(List<OrderItem> items) {
        items.forEach(item -> deduct(item.productId(), item.quantity()));
    }

    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }
}

