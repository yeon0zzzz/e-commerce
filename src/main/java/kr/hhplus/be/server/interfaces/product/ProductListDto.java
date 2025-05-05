package kr.hhplus.be.server.interfaces.product;

import kr.hhplus.be.server.domain.product.Product;
import lombok.Builder;

import java.util.List;

public class ProductListDto {

    @Builder
    public record Response(
            List<ProductDto.Response> products
    ) {
        public static Response of(List<Product> products) {
            List<ProductDto.Response> productList = products.stream()
                    .map(ProductDto.Response::of)
                    .toList();

            return Response.builder()
                    .products(productList)
                    .build();
        }
    }
}
