package kr.hhplus.be.server.domain.point;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PointMessage {
    public static final String INVALID_AMOUNT = "포인트는 0보다 커야 합니다.";
    public static final String EXCEEDS_LIMIT = "충전 가능 포인트를 초과했습니다.";
    public static final String INSUFFICIENT = "사용 가능한 포인트가 부족합니다.";
}
