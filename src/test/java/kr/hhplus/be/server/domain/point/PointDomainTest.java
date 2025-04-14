package kr.hhplus.be.server.domain.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
@DisplayName("포인트_도메인_유효성_검증_단위_테스트")
class PointDomainTest {

    private final Point point = Point.builder()
            .pointId(1L)
            .userId(1L)
            .point(0L)
            .updatedAt(LocalDateTime.now())
            .build();

    @Test
    @DisplayName("0원_충전_예외처리_테스트")
    void zeroPointChargeThrowException() {
        assertThatThrownBy(() -> point.charge(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("음수_충전_예외처리_테스트")
    void negativePointChargeThrowException() {
        assertThatThrownBy(() -> point.charge(-100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("0원_사용_예외처리_테스트")
    void zeroPointUseThrowException() {
        assertThatThrownBy(() -> point.use(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("음수_사용_예외처리_테스트")
    void negativePointUseThrowException() {
        assertThatThrownBy(() -> point.use(-500L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.INVALID_AMOUNT);
    }

    @Test
    @DisplayName("충전_한도_초과_예외처리_테스트")
    void exceedLimitPointChargeThrowException() {
        assertThatThrownBy(() -> point.charge(1_000_001L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.EXCEEDS_LIMIT);
    }

    @Test
    @DisplayName("잔액_부족_예외처리_테스트")
    void insufficientPointUseThrowException() {
        assertThatThrownBy(() -> point.use(100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PointMessage.INSUFFICIENT);
    }
}