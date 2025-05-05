package kr.hhplus.be.server.domain.popular;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("인기 상품 통합 테스트")
@Transactional
public class PopularProductServiceIntegrationTest {

    @Autowired
    private PopularProductService popularProductService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Test
    @DisplayName("최근 3일간 가장 많이 팔린 인기 상품 조회")
    @Sql(scripts = "/sql/popularProducts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findPopularProduct() {
        List<PopularProduct> popularProducts = popularProductService.getPopularProducts();

        assertThat(popularProducts).hasSize(5);
    }
}
