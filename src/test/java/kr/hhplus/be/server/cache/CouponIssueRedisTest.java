package kr.hhplus.be.server.cache;

import kr.hhplus.be.server.infra.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@DisplayName("Redis 기반 선착순 쿠폰 발급 단위 테스트")
public class CouponIssueRedisTest {

    @Autowired
    private RedisRepository redisRepository;

    private final Long couponId = 10L;
    private final String metaKey = "coupon:meta:" + couponId;
    private final String issueKey = "coupon:issue:" + couponId;

    @BeforeEach
    void setUp() {
        redisRepository.remove(metaKey);
        redisRepository.remove(issueKey);

        // 쿠폰 메타데이터 Redis 저장
        redisRepository.addHash(metaKey, "limit", "3");
        redisRepository.addHash(metaKey, "expiresAt", LocalDate.now().plusDays(1).toString());
    }

    @Test
    @DisplayName("쿠폰 발급 성공 테스트")
    void testCouponIssueFlow() {
        for (int i = 1; i <= 4; i++) {
            String userId = "user" + i;

            // 1. 중복 확인
            if (Boolean.TRUE.equals(redisRepository.getSortedSetScore(issueKey, userId) != null)) {
                System.out.println(userId + ": 이미 발급됨");
                continue;
            }

            // 2. 최대 수량 초과 확인
            int limit = Integer.parseInt((String) Objects.requireNonNull(redisRepository.getHashValue(metaKey, "limit")));
            Long issued = redisRepository.getSortedSetCount(issueKey);

            if (issued >= limit) {
                System.out.println(userId + ": 선착순 마감");
                continue;
            }

            // 3. 발급 처리
            boolean added = Boolean.TRUE.equals(
                    redisRepository.addSortedSet(issueKey, userId, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
            );
        }

        // 최종 발급된 유저 수 검증
        Long total = redisRepository.getSortedSetCount(issueKey);
        assertThat(total).isEqualTo(3L);
    }
}
