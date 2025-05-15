package kr.hhplus.be.server.cache;

import kr.hhplus.be.server.domain.product.stats.ProductSalesRepository;
import kr.hhplus.be.server.domain.product.stats.ProductSalesService;
import kr.hhplus.be.server.infra.RedisRepository;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("상품 일별 판매량 캐시 저장 통합 테스트")
public class IncreaseDailySalesTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private ProductSalesRepository productSalesRepository;

    @Autowired
    private ProductSalesService productSalesService;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @AfterEach
    void setUp() {
        String key = "product:sales:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        redisRepository.remove(key);
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("주문_완료시_인기상품_캐시에_ZINCRBY_적용된다")
    @Sql(scripts = "/sql/orderPayment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void productSalesDailyCacheComplete() throws Exception {
        mockMvc.perform(post("/api/v1/orders/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "userId": 1,
                  "items": [
                    {
                      "productId": 11,
                      "quantity": 2,
                      "price": 10000
                    },
                    {
                      "productId": 12,
                      "quantity": 1,
                      "price": 20000
                    }
                  ],
                  "userCouponId": 5,
                  "discountAmount": 3000
                }
                """))
                .andExpect(status().isOk());

        // then
        String day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "product:sales:" + day;

        Double score11 = redisRepository.getSortedSetScore(key, "11");
        Double score22 = redisRepository.getSortedSetScore(key, "12");

        assertThat(score11).isEqualTo(2.0);
        assertThat(score22).isEqualTo(1.0);
    }
}
