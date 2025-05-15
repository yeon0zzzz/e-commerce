package kr.hhplus.be.server.infra.coupon.usercoupon;

import kr.hhplus.be.server.infra.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserCouponRedisRepositoryImpl {

    private final RedisRepository redisRepository;

    public Integer getCouponLimit(String key, String hashKey) {
        String limitStr = (String) redisRepository.getHashValue(key, "limit");
        return limitStr != null ? Integer.parseInt(limitStr) : null;
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
