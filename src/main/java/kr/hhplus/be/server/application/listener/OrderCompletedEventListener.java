package kr.hhplus.be.server.application.listener;

import kr.hhplus.be.server.application.dataplatform.DataPlatformClient;
import kr.hhplus.be.server.application.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCompletedEventListener {

    private final DataPlatformClient dataPlatformClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void handle(OrderEvent.Completed event) {
        dataPlatformClient.sendOrderInvoice(event);
    }
}
