package kr.hhplus.be.server.interfaces.popular;

import kr.hhplus.be.server.domain.popular.PopularProduct;
import lombok.Builder;

import java.util.List;

public class PopularProductListDto {

    @Builder
    public record Response(
            List<PopularProductDto> popularProducts
    ) {
        public static Response of(List<PopularProduct> popularProducts) {
            List<PopularProductDto> popularProductList = popularProducts.stream()
                    .map(p -> PopularProductDto.builder()
                            .productId(p.productId())
                            .name(p.name())
                            .price(p.price())
                            .totalQuantity(p.totalQuantity())
                            .build())
                    .toList();

            return Response.builder()
                    .popularProducts(popularProductList)
                    .build();
        }
    }
}
