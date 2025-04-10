package kr.hhplus.be.server.interfaces.payment;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping("")
    public ApiResponse getPayments(@RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("paymentId", 1);
        response.put("status", "SUCCESS");
        response.put("paidAmount", 15000);
        response.put("paidAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }

    @PostMapping
    public ApiResponse pay(@RequestBody Map<String, Object> request) {
        Long orderId = Long.valueOf(request.get("orderId").toString());
        String method = request.get("paymentMethod").toString();

        Map<String, Object> response = new HashMap<>();
        response.put("paymentId", 2001);
        response.put("status", "SUCCESS");
        response.put("paidAmount", 15000);
        response.put("paidAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }
    @GetMapping("/{paymentId}")
    public ApiResponse getPaymentDetail(@PathVariable Long paymentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("paymentId", paymentId);
        response.put("status", "SUCCESS");
        response.put("paidAmount", 15000);
        response.put("paidAt", LocalDateTime.now());

        return ApiResponse.success(response);
    }
}
