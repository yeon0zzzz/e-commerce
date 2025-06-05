package kr.hhplus.be.server.interfaces.product;

import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public ApiResponse<ProductDto.Response> getProduct(@PathVariable Long productId) {

        Product product = productService.findById(productId);

        return ApiResponse.success(ProductDto.Response.of(product));
    }

    @GetMapping("/products")
    public ApiResponse<ProductListDto.Response> getProducts() {

        List<Product> products = productService.getAll();

        return ApiResponse.success(ProductListDto.Response.of(products));
    }
}
