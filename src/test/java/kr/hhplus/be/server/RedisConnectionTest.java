package kr.hhplus.be.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void redisTest() {
        String k = "test:key";
        String v = "Redis Running";

        redisTemplate.opsForValue().set(k, v);

        Object result = redisTemplate.opsForValue().get(k);

        assertThat(result).isEqualTo(v);
    }
}
