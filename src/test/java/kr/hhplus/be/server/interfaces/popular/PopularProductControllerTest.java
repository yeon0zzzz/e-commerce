package kr.hhplus.be.server.interfaces.popular;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("인기 상품 컨트롤러 통합 테스트")
public class PopularProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private final String prefix = "/api/v1";

    @AfterEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("가장 많이 팔린 Top5 인기 상품 조회 성공")
    @Sql(scripts = "/sql/popularProducts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void success_get_popular_products_test() throws Exception {

        mockMvc.perform(get(prefix+"/stats/products/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.popularProducts.length()").value(5));
    }
}
