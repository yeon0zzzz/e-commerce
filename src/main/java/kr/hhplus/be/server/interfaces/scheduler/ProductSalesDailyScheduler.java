package kr.hhplus.be.server.interfaces.scheduler;

import kr.hhplus.be.server.domain.product.stats.ProductSalesDaily;
import kr.hhplus.be.server.domain.product.stats.ProductSalesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProductSalesDailyScheduler {

    private final ProductSalesService productSalesService;

    /*
    * 매일 00시에 스케줄러 실행
    */
    @Scheduled(cron = "0 0 0 * * *")
    public void syncDailySales() {

        try {
            String key = "product:sales:" + LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            List<ProductSalesDaily> products = productSalesService.findAll(key);

            productSalesService.saveAll(products);
            log.info("일별 판매량 집계 완료 [key: {} - 총 {}건]", key, products.size());
        } catch (Exception e) {
            log.error("상품 판매 일별 집계 스케줄러 실행 오류", e);
        }
    }

}
