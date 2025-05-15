package kr.hhplus.be.server.cache;

import kr.hhplus.be.server.application.coupon.UserCouponCacheFacade;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCouponCacheService;
import kr.hhplus.be.server.infra.RedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@DisplayName("Redis 기반 선착순 쿠폰 발급 단위 테스트")
public class UserCouponCacheServiceIntegrationTest {

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private UserCouponCacheFacade userCouponCacheFacade;

    @Autowired
    private UserCouponCacheService userCouponCacheService;

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
    @DisplayName("발급 한도 초과 이후는 실패")
    void testCouponIssueLimit() {
        // 발급 성공
        for (int i = 1; i <= 3; i++) {
            Long userId = (long) i;
            userCouponCacheFacade.publish(userId, couponId);
        }

        // 4번째 유저는 실패해야 함
        assertThrows(IllegalStateException.class, () -> {
            userCouponCacheFacade.publish(4L, couponId);
        });

        // Redis 에 실제 발급된 사용자 수 확인
        Long count = userCouponCacheService.getIssuedCount(couponId);
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("중복 발급 방지 테스트")
    void testDuplicateIssue() {
        Long userId = 999L;

        // 첫 발급은 성공
        userCouponCacheFacade.publish(userId, couponId);

        // 두 번째는 예외 발생
        assertThrows(IllegalStateException.class, () -> {
            userCouponCacheFacade.publish(userId, couponId);
        });

        // Redis 에 한 명만 기록되어 있어야 함
        Long count = userCouponCacheService.getIssuedCount(couponId);
        assertThat(count).isEqualTo(1);
    }
}
