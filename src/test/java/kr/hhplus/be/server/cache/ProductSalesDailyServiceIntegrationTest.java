package kr.hhplus.be.server.cache;


import kr.hhplus.be.server.domain.product.stats.ProductSalesDaily;
import kr.hhplus.be.server.domain.product.stats.ProductSalesService;
import kr.hhplus.be.server.infra.RedisRepository;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("상품 일별 집계 서비스 통합 테스트")
public class ProductSalesDailyServiceIntegrationTest {

    @Autowired
    private ProductSalesService productSalesService;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        String key = "product:sales:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        redisRepository.remove(key);
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("Redis 캐시에서 오늘 판매된 상품을 조회하여 DB에 저장한다")
    void findAllDailySalesProductCache() {
        // given
        String key = "product:sales:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        redisRepository.incrementSortedSet(key, "101", 3L);
        redisRepository.incrementSortedSet(key, "102", 5L);
        redisRepository.expire(key, Duration.ofDays(2));

        // when
        List<ProductSalesDaily> salesList = productSalesService.findAll(key);
        productSalesService.saveAll(salesList);
        ProductSalesDaily productSalesDaily1 = productSalesService.findById(1L);
        ProductSalesDaily productSalesDaily2 = productSalesService.findById(2L);

        // then
        assertThat(salesList).hasSize(2);
        assertThat(productSalesDaily1.quantity()).isEqualTo(3L);
        assertThat(productSalesDaily2.quantity()).isEqualTo(5L);
    }
}
