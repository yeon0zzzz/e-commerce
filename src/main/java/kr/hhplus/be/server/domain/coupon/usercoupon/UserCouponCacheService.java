package kr.hhplus.be.server.domain.coupon.usercoupon;

import kr.hhplus.be.server.domain.coupon.CouponStatus;
import kr.hhplus.be.server.infra.coupon.usercoupon.UserCouponRedisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        String limit = userCouponRedisRepository.getCouponHash(metaKey, "limit");
        if (limit == null) throw new IllegalStateException("쿠폰 메타 정보 없음");

        // 2. 중복 발급 방지
        if (Boolean.TRUE.equals(userCouponRedisRepository.getSortedSetScore(issueKey, String.valueOf(userId)) != null)) {
            throw new IllegalStateException("이미 발급된 사용자입니다.");
        }

        // 3. 수량 초과 확인
        Long issued = userCouponRedisRepository.getSortedSetCount(issueKey);
        if (issued != null && issued >= Integer.parseInt(limit)) {
            throw new IllegalStateException("발급 수량 초과입니다.");
        }

        // 4. 발급 성공 처리
        return Boolean.TRUE.equals(userCouponRedisRepository.addSortedSet(issueKey, userId.toString(), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
    }

    public Boolean atomicPublish(Long userId, Long couponId) {
        String issueKey = "coupon:issue:" + couponId;
        String metaKey = "coupon:meta:" + couponId;

        String limit = userCouponRedisRepository.getCouponHash(metaKey, "limit");
        String expiresAt = userCouponRedisRepository.getCouponHash(metaKey, "expiresAt");
        String status = userCouponRedisRepository.getCouponHash(metaKey, "status");
        if (limit == null || expiresAt == null || status == null) throw new IllegalStateException("쿠폰 메타 정보 없음");

        if (!CouponStatus.ACTIVE.name().equals(status)) {
            throw new IllegalStateException("비활성화된 쿠폰입니다.");
        }

        LocalDate expiresAtDate = LocalDate.parse(expiresAt);
        if (LocalDate.now().isAfter(expiresAtDate)) {
            throw new IllegalStateException("만료된 쿠폰입니다.");
        }

        long score = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Long result = userCouponRedisRepository.issueCouponAtomic(issueKey, metaKey, userId.toString(), score, Integer.parseInt(limit));

        if (result == null) throw new IllegalStateException("Redis 스크립트 실행 실패");
        if (result == -1) throw new IllegalStateException("이미 발급된 사용자입니다.");
        if (result == -2) throw new IllegalStateException("발급 수량 초과입니다.");

        return true;
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
