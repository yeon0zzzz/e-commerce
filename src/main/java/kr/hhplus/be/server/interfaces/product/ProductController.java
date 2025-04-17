package kr.hhplus.be.server.interfaces.product;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public ApiResponse getProducts() {
        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1);
        product.put("name", "상품 A");
        product.put("price", 10000);
        product.put("status", "ACTIVE");

        return ApiResponse.success(List.of(product));
    }

    @GetMapping("/popular")
    public ApiResponse getPopularProducts() {
        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1);
        product.put("name", "인기상품 A");
        product.put("salesCount", 50);

        return ApiResponse.success(List.of(product));
    }
}
