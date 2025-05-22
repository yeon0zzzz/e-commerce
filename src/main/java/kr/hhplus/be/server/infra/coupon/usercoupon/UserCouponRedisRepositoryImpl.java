package kr.hhplus.be.server.infra.coupon.usercoupon;

import kr.hhplus.be.server.infra.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserCouponRedisRepositoryImpl {

    private final RedisRepository redisRepository;

    @Qualifier("couponIssueScript")
    private final DefaultRedisScript<Long> couponIssueScript;

    public Long issueCouponAtomic(String issueKey, String metaKey, String userId, Long score, int limit) {
        return redisRepository.executeScript(
                couponIssueScript,
                List.of(issueKey, metaKey),
                userId,
                score,
                limit
        );
    }

    public String getCouponHash(String key, String hashKey) {
        return (String) redisRepository.getHashValue(key, hashKey);
    }

    public Long getSortedSetCount(String key) {
        return redisRepository.getSortedSetCount(key);
    }

    public Boolean addSortedSet(String key, String userId, Long issuedAt) {
        return redisRepository.addSortedSet(key, userId, issuedAt);
    }

    public Double getSortedSetScore(String key, String value) {
        return redisRepository.getSortedSetScore(key, value);
    }

    public Set<Object> getIssuedList(String key) {
        return redisRepository.getSortedSetRange(key, 0L, -1L);
    }
}
