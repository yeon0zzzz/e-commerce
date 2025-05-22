package kr.hhplus.be.server.domain.product.stats.event;

import kr.hhplus.be.server.application.event.ProductSalesDailyEvent;
import kr.hhplus.be.server.domain.product.stats.ProductSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ProductSalesDailyEventListener {

    private final ProductSalesService productSalesService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(ProductSalesDailyEvent event) {
        productSalesService.increaseDailySales(event.items());
    }
}
