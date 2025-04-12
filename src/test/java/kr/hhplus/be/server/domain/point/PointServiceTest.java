package kr.hhplus.be.server.domain.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("포인트_서비스_단위_테스트")
class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Test
    @DisplayName("사용자_포인트_충전_성공_테스트")
    void charge() {
        // given
        long userId = 1L;
        long amount = 100L;

        Point point = Point.builder()
                .userId(userId)
                .point(0L)
                .updatedAt(LocalDateTime.now())
                .build();

        given(pointRepository.findByUserId(userId)).willReturn(point);

        // when
        Point updatePoint = pointService.charge(userId, amount);

        //then
        assertThat(updatePoint.userId()).isEqualTo(userId);
        assertThat(updatePoint.point()).isEqualTo(amount);

        verify(pointRepository).findByUserId(eq(userId));
        verify(pointRepository).save(eq(updatePoint));
    }

    @Test
    @DisplayName("사용자_포인트_사용_성공_테스트")
    void use() {
        // given
        long userId = 1L;
        long amount = 100L;

        Point point = Point.builder()
                .userId(userId)
                .point(300L)
                .updatedAt(LocalDateTime.now())
                .build();

        given(pointRepository.findByUserId(userId)).willReturn(point);

        // when
        Point updatePoint = pointService.use(userId, amount);

        //then
        assertThat(updatePoint.userId()).isEqualTo(userId);
        assertThat(updatePoint.point()).isEqualTo(300L - amount);

        verify(pointRepository).findByUserId(eq(userId));
        verify(pointRepository).save(eq(updatePoint));
    }

    @Test
    @DisplayName("사용자_포인트_조회_테스트")
    void getPoint() {
        // given
        long userId = 1L;
        long amount = 100L;

        Point point = Point.builder()
                .userId(userId)
                .point(amount)
                .updatedAt(LocalDateTime.now())
                .build();

        given(pointRepository.findByUserId(userId)).willReturn(point);

        // when
        Point userPoint = pointService.getUserPoint(userId);

        //then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.userId()).isEqualTo(userId);
        assertThat(userPoint.point()).isEqualTo(amount);

        verify(pointRepository).findByUserId(eq(userId));
    }



}