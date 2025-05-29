package kr.hhplus.be.server.application.event.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hhplus.be.server.domain.coupon.usercoupon.UserCoupon;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCouponIssuedMessage {

    private final Long userId;
    private final Long couponId;
    private final LocalDateTime issuedAt;

    @JsonCreator
    @Builder
    public UserCouponIssuedMessage(
            @JsonProperty("userId") Long userId,
            @JsonProperty("couponId") Long couponId,
            @JsonProperty("issuedAt") LocalDateTime issuedAt
    ) {
        this.userId = userId;
        this.couponId = couponId;
        this.issuedAt = issuedAt;
    }

    public static UserCouponIssuedMessage from(UserCoupon userCoupon) {
        return UserCouponIssuedMessage.builder()
                .userId(userCoupon.userId())
                .couponId(userCoupon.couponId())
                .issuedAt(userCoupon.createdAt()) // 발급일은 createdAt 기준
                .build();
    }
}
