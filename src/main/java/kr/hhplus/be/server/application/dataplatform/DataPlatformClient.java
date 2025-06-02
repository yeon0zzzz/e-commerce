package kr.hhplus.be.server.application.dataplatform;

import kr.hhplus.be.server.application.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataPlatformClient {

    public void sendOrderInvoice(OrderEvent.Completed event) {
        try {
            log.info("[OrderEvent] 외부 플랫폼 전용 MockAPI 주문 정보 전송- orderId: {}", event.order().orderId());

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("주문 예외 발생 : {}", e.getMessage(), e);
        }
    }
}
