package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.product.stats.ProductSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class OrderCompletedEventListener {

    private final ProductSalesService productSalesService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(OrderCompletedEvent event) {
        productSalesService.increaseDailySales(event.items());
    }
}
