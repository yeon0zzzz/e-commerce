package kr.hhplus.be.server.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void put(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public boolean keyExists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void addSortedSet(String key, String value, Long score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public void incrementSortedSet(String key, String value, Long score) {
        redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public void expire(String key, Duration ttl) {
        redisTemplate.expire(key, ttl);
    }

    public Double getSortedSetScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Set<ZSetOperations.TypedTuple<Object>> getSortedSetRangeWithScore(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }
}
