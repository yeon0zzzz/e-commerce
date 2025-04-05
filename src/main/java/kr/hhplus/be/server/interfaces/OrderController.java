package kr.hhplus.be.server.interfaces;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/orders")
public class OrderController {

    @PostMapping("/orders")
    public ApiResponse createOrder(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", 1001);
        response.put("finalAmount", 15000);
        response.put("createdAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }

    @GetMapping("/orders/{orderId}")
    public ApiResponse getOrderDetail(@PathVariable Long orderId) {
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("userId", 1);
        order.put("finalAmount", 15000);
        order.put("status", "PAID");
        order.put("createdAt", LocalDateTime.now());

        return ApiResponse.success(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ApiResponse getUserOrders(@PathVariable Long userId) {
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", 1001);
        order.put("finalAmount", 15000);
        order.put("createdAt", LocalDateTime.now());

        return ApiResponse.success(List.of(order));
    }

    @GetMapping("/orders/{orderId}/events")
    public ApiResponse getOrderEvents(@PathVariable Long orderId) {
        Map<String, Object> event1 = new HashMap<>();
        event1.put("status", "CREATED");
        event1.put("changedAt", LocalDateTime.now().minusMinutes(10));

        Map<String, Object> event2 = new HashMap<>();
        event2.put("status", "PAID");
        event2.put("changedAt", LocalDateTime.now());

        return ApiResponse.success(List.of(event1, event2));
    }
}
