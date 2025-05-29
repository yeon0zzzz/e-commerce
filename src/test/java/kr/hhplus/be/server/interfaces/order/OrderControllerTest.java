package kr.hhplus.be.server.interfaces.order;

import kr.hhplus.be.server.infra.message.consumer.OrderCompletedConsumer;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("주문 컨트롤러 통합 테스트")
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    private OrderCompletedConsumer orderCompletedConsumer;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("주문 결제 성공")
    @Sql(scripts = "/sql/orderPayment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void success_get_popular_products_test() throws Exception {

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.finalAmount").value(37000));
    }

    @Test
    @DisplayName("주문 결제 성공 시 외부 플랫폼 전송용 Kafka 메시지 발행 확인")
    @Sql(scripts = "/sql/orderPayment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void success_order_payment_publish_external_dataPlatform() throws Exception {
        // When: 주문 결제 요청
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.finalAmount").value(37000));

        // Kafka 메시지가 Consumer 에서 처리되었는지 확인
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() ->
                        Mockito.verify(orderCompletedConsumer, Mockito.atLeastOnce())
                                .handle(Mockito.argThat(msg ->
                                        msg.orderId().equals(1L) &&
                                                msg.userId().equals(1L)
                                ))
                );
    }
}
