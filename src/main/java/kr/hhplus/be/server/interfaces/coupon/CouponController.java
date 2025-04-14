package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class CouponController {

    @GetMapping("/users/{userId}/coupons")
    public ApiResponse getCoupons(@RequestParam Long userId, @RequestParam String couponStatus) {
        Map<String, Object> coupon = new HashMap<>();
        coupon.put("userCouponId", 1);
        coupon.put("couponId", 10);
        coupon.put("used", false);
        coupon.put("issuedAt", LocalDateTime.now());

        return ApiResponse.success(List.of(coupon));
    }

    @PostMapping("/users/{userId}/coupons")
    public ApiResponse issueCoupon(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        Long couponId = Long.valueOf(request.get("couponId").toString());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("couponId", couponId);
        data.put("issuedAt", LocalDateTime.now());

        return ApiResponse.success(data);
    }
}
