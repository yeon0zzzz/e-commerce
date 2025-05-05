package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("상품 조회")
    void findProduct() {
        Product product = Product.builder()
                .productId(null)
                .name("상품1")
                .price(BigDecimal.valueOf(1000L))
                .productStatus(ProductStatus.ACTIVE)
                .build();
        Product savedProduct = productService.save(product);

        Product result = productService.findById(savedProduct.productId());
        assertThat(result.productId()).isEqualTo(1L);
        assertThat(result.productStatus()).isEqualTo(ProductStatus.ACTIVE);
    }
}
