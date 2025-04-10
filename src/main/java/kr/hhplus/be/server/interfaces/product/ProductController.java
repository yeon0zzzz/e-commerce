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

    @GetMapping("/{productId}")
    public ApiResponse getProductDetail(@PathVariable Long productId) {
        Map<String, Object> product = new HashMap<>();
        product.put("productId", productId);
        product.put("name", "상품 A");
        product.put("description", "이 상품은 A입니다.");
        product.put("price", 10000);
        product.put("status", "ACTIVE");

        return ApiResponse.success(product);
    }

    @GetMapping("/popular")
    public ApiResponse getPopularProducts() {
        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1);
        product.put("name", "인기상품 A");
        product.put("salesCount", 50);

        return ApiResponse.success(List.of(product));
    }

    @GetMapping("/{productId}/inventory")
    public ApiResponse getInventory(@PathVariable Long productId) {
        Map<String, Object> inventory = new HashMap<>();
        inventory.put("productId", productId);
        inventory.put("quantity", 100);
        inventory.put("reserved", 10);

        return ApiResponse.success(inventory);
    }
}
