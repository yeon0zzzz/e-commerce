package kr.hhplus.be.server.interfaces.order;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("주문 컨트롤러 통합 테스트")
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

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
}
