package kr.hhplus.be.server.domain.product;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@DisplayName("상품_서비스_단위_테스트")
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품_조회_성공_테스트")
    void getProduct() {
        // given
        Product mockProduct = Product.builder()
                .productId(1L)
                .name("테스트 상품")
                .price(BigDecimal.valueOf(5000))
                .productStatus(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(productRepository.findById(1L)).willReturn(mockProduct);

        // when
        Product result = productService.getById(1L);

        // then
        assertThat(result.name()).isEqualTo("테스트 상품");
        verify(productRepository).findById(1L);
    }
}