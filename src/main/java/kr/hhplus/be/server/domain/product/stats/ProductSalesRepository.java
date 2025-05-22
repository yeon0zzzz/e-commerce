package kr.hhplus.be.server.domain.product.stats;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductSalesRepository {
    Long increaseDailySales(String key, String value, Long score);
    List<ProductSalesDaily> findAll(String key);
    void saveAll(List<ProductSalesDaily> products);
    ProductSalesDaily findById(Long id);
}
