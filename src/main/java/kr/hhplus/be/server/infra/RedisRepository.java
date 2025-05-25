package kr.hhplus.be.server.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

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

    public Boolean addSortedSet(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, score);
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

    public Double getSortedSetScore(String key, Long value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Set<ZSetOperations.TypedTuple<Object>> getSortedSetRangeWithScore(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Set<Object> getSortedSetRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Long getSortedSetCount(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void addHash(String key, String hashKey, String hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public Long executeScript(DefaultRedisScript<Long> redisScript, List<String> keys, Object... args) {
        return redisTemplate.execute(redisScript, keys, args);
    }
}
