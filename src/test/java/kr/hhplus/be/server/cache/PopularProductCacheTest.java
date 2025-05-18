package kr.hhplus.be.server.cache;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.infra.RedisRepository;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("인기 상품 호출 캐시 테스트")
public class PopularProductCacheTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    DatabaseCleaner databaseCleaner;

    private final String redisCacheKey = "popularProducts::popularProducts";  // 기본 key 규칙

    @AfterEach
    void setUp() {
        redisRepository.remove(redisCacheKey);
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("인기 상품 API 호출 후 Redis에 캐시가 저장된다.")
    @Sql(scripts = "/sql/popularProducts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getPopularProducts_shouldBeCachedInRedis() throws Exception {

        // 1. 캐시 사전 존재 여부 확인
        assertThat(redisRepository.keyExists(redisCacheKey)).isFalse();

        // 2. 컨트롤러 호출
        mockMvc.perform(get("/stats/products/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.popularProducts.length()").value(5));

        // 3. 캐시 존재 여부 확인
        assertThat(redisRepository.keyExists(redisCacheKey)).isTrue();

    }
}
