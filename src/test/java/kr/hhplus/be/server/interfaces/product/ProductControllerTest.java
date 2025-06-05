package kr.hhplus.be.server.interfaces.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductRepository;
import kr.hhplus.be.server.domain.product.ProductStatus;
import kr.hhplus.be.server.support.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("상품 컨트롤러 통합 테스트")
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private final String prefix = "/api/v1";

    @BeforeEach
    void setUp() {
        databaseCleaner.truncateAllTables();
    }

    @Test
    @DisplayName("상품 조회 성공")
    void success_get_product_test() throws Exception {
        Product product = productRepository.save(
                Product.builder()
                        .name("product_1")
                        .price(BigDecimal.valueOf(1000L))
                        .productStatus(ProductStatus.ACTIVE)
                        .build()
        );
        mockMvc.perform(get(prefix+"/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("product_1"))
                .andExpect(jsonPath("$.data.price").value(1000L))
                .andExpect(jsonPath("$.data.productStatus").value("ACTIVE"));
    }

    @Test
    @DisplayName("상품 목록 조회 성공")
    void success_get_products_test() throws Exception {
        Product product1 = productRepository.save(
                Product.create("product_1", BigDecimal.valueOf(1000L))
        );
        Product product2 = productRepository.save(
                Product.create("product_2", BigDecimal.valueOf(1000L))
        );

        mockMvc.perform(get(prefix+"/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.products.length()").value(2))
                .andExpect(jsonPath("$.data.products[0].name").value("product_1"))
                .andExpect(jsonPath("$.data.products[0].price").value(1000L))
                .andExpect(jsonPath("$.data.products[1].name").value("product_2"))
                .andExpect(jsonPath("$.data.products[1].price").value(1000L));
    }
}
