package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@DisplayName("상품_도메인_유효성_검증_단위_테스트")
class ProductDomainTest {
    private final Product product = Product.builder()
            .productId(1L)
            .name("노출금지 상품")
            .price(BigDecimal.valueOf(10000))
            .productStatus(ProductStatus.INACTIVE)
            .build();

    @Test
    @DisplayName("비활성_상품_조회_예외처리_테스트")
    void inactiveProductThrowException() {
        assertThatThrownBy(product::validateIsActive)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ProductMessage.INACTIVE_PRODUCT);
    }
}