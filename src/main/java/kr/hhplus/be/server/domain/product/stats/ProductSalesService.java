package kr.hhplus.be.server.domain.product.stats;

import kr.hhplus.be.server.domain.order.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductSalesService {

    private final ProductSalesRepository productSalesRepository;

    public void increaseDailySales(List<OrderItem> items) {
        String key = "product:sales:" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        items.forEach(item -> {
            Long result = productSalesRepository.increaseDailySales(key, item.productId().toString(), item.quantity());
            if (result == null || result == 0) {
                throw new IllegalStateException("Redis 집계 실패: key=" + key + ", productId=" + item.productId());
            }
        });
    }

    public List<ProductSalesDaily> findAll(String key) {
        return productSalesRepository.findAll(key);
    }

    public void saveAll(List<ProductSalesDaily> products) {
        productSalesRepository.saveAll(products);
    }

    public ProductSalesDaily findById(Long id) {
        return productSalesRepository.findById(id);
    }
}
