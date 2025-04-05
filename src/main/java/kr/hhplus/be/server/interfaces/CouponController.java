package kr.hhplus.be.server.interfaces;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/coupons")
public class CouponController {

    @PostMapping("/coupons/issue")
    public ApiResponse issueCoupon(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long couponId = Long.valueOf(request.get("couponId").toString());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("couponId", couponId);
        data.put("issuedAt", LocalDateTime.now());

        return ApiResponse.success(data);
    }

    @GetMapping("/users/{userId}/coupons")
    public ApiResponse getUserCoupons(@PathVariable Long userId) {
        Map<String, Object> coupon = new HashMap<>();
        coupon.put("userCouponId", 1);
        coupon.put("couponId", 10);
        coupon.put("used", false);
        coupon.put("issuedAt", LocalDateTime.now());

        return ApiResponse.success(List.of(coupon));
    }

    @GetMapping("/users/{userId}/coupons/available")
    public ApiResponse getAvailableCoupons(@PathVariable Long userId) {
        Map<String, Object> coupon = new HashMap<>();
        coupon.put("userCouponId", 1);
        coupon.put("couponId", 10);
        coupon.put("discountAmount", 1000);

        return ApiResponse.success(List.of(coupon));
    }

    @PatchMapping("/users/{userId}/coupons/{userCouponId}/use")
    public ApiResponse useCoupon(@PathVariable Long userId, @PathVariable Long userCouponId) {
        Map<String, Object> data = new HashMap<>();
        data.put("userCouponId", userCouponId);
        data.put("used", true);
        data.put("usedAt", LocalDateTime.now());

        return ApiResponse.success(data);
    }
}
