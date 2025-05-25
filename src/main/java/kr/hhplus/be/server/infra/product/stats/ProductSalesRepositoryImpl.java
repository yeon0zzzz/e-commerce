package kr.hhplus.be.server.infra.product.stats;

import kr.hhplus.be.server.domain.product.stats.ProductSalesDaily;
import kr.hhplus.be.server.domain.product.stats.ProductSalesRepository;
import kr.hhplus.be.server.infra.RedisRepository;
import kr.hhplus.be.server.infra.product.stats.jpa.ProductSalesDailyEntity;
import kr.hhplus.be.server.infra.product.stats.jpa.ProductSalesDailyJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class ProductSalesRepositoryImpl implements ProductSalesRepository {

    private final RedisRepository redisRepository;
    private final ProductSalesDailyJpaRepository productSalesDailyJpaRepository;

    @Qualifier("incrementWithExpireScript")
    private final DefaultRedisScript<Long> incrementWithExpireScript;

    public Long increaseDailySales(String key, String value, Long score) {
        return redisRepository.executeScript(
                incrementWithExpireScript,
                List.of(key),                                       // KEYS[1]
                score,                                              // ARGV[1]
                value,                                              // ARGV[2]
                Duration.ofDays(2).getSeconds()                     // ARGV[3]
        );
    }

    public List<ProductSalesDaily> findAll(String key) {
        Set<ZSetOperations.TypedTuple<Object>> salesData = redisRepository.getSortedSetRangeWithScore(key, 0L, -1L);

        if (salesData == null) return List.of();

        LocalDateTime salesAt = LocalDate.parse(key.substring("product:sales:".length()),
                        DateTimeFormatter.ofPattern("yyyyMMdd"))
                .atStartOfDay();

        return salesData.stream()
                .filter(entry -> entry.getValue() != null && entry.getScore() != null)
                .map(entry -> ProductSalesDaily.create(
                        salesAt,
                        Long.valueOf(entry.getValue().toString()),
                        entry.getScore().longValue()
                ))
                .toList();
    }

    public void saveAll(List<ProductSalesDaily> products) {
        List<ProductSalesDailyEntity> entities2 = products.stream()
                .map(ProductSalesDailyEntity::toEntity)
                .toList();

        productSalesDailyJpaRepository.saveAll(entities2);
    }

    public ProductSalesDaily findById(Long id) {
       return productSalesDailyJpaRepository.findById(id)
               .map(ProductSalesDailyEntity::toDomain)
               .orElseThrow(() -> new IllegalArgumentException("일별 집계 상품이 없습니다."));
    }

}
