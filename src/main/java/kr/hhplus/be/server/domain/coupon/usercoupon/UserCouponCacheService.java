package kr.hhplus.be.server.domain.coupon.usercoupon;

import kr.hhplus.be.server.infra.coupon.usercoupon.UserCouponRedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserCouponCacheService {

    private final UserCouponRedisRepositoryImpl userCouponRedisRepository;

    public Boolean publish(Long userId, Long couponId) {
        String metaKey = "coupon:meta:" + couponId;
        String issueKey = "coupon:issue:" + couponId;

        // 1. 메타 캐시 없으면 실패 (미리 적재 전제)
        Integer limit = userCouponRedisRepository.getCouponLimit(metaKey, "limit");
        if (limit == null) throw new IllegalStateException("쿠폰 메타 정보 없음");

        // 2. 중복 발급 방지
        if (Boolean.TRUE.equals(userCouponRedisRepository.getSortedSetScore(issueKey, String.valueOf(userId)) != null)) {
            throw new IllegalStateException("이미 발급된 사용자입니다.");
        }

        // 3. 수량 초과 확인
        Long issued = userCouponRedisRepository.getSortedSetCount(issueKey);
        if (issued != null && issued >= limit) {
            throw new IllegalStateException("발급 수량 초과입니다.");
        }

        // 4. 발급 성공 처리
        return Boolean.TRUE.equals(userCouponRedisRepository.addSortedSet(issueKey, userId.toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
    }

    public Long getIssuedCount(Long couponId) {
        String issueKey = "coupon:issue:" + couponId;
        return userCouponRedisRepository.getSortedSetCount(issueKey);
    }

    public Set<Object> getIssuedList(Long couponId) {
        String issueKey = "coupon:issue:" + couponId;
        return userCouponRedisRepository.getIssuedList(issueKey);
    }

}
