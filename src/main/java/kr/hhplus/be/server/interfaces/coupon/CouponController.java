package kr.hhplus.be.server.interfaces.coupon;

import kr.hhplus.be.server.application.coupon.UserCouponCacheFacade;
import kr.hhplus.be.server.application.coupon.UserCouponFacade;
import kr.hhplus.be.server.common.ApiResponse;
import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService couponService;
    private final UserCouponFacade userCouponFacade;
    private final UserCouponCacheFacade userCouponCacheFacade;

    @GetMapping("/coupons/{couponId}")
    public ApiResponse<CouponDto.Response> getCoupon(@PathVariable Long couponId) {

        Coupon coupon = couponService.findById(couponId);

        return ApiResponse.success(CouponDto.Response.of(coupon));
    }

    @PostMapping("/coupons/{couponId}/issue")
    public ApiResponse<UserCouponDto.Response> issueCoupon(@PathVariable Long couponId, @RequestBody UserCouponDto.Request request) {

        UserCoupon userCoupon = userCouponFacade.issue(request.userId(), couponId);

        return ApiResponse.success(UserCouponDto.Response.of(userCoupon));
    }

    @PostMapping("/coupons/{couponId}/publish")
    public ApiResponse<Void> publishCoupon(@PathVariable Long couponId, @RequestBody UserCouponDto.Request request) {
        userCouponCacheFacade.publish(request.userId(), couponId);
        return ApiResponse.success();
    }
}
