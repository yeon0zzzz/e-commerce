package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.infra.point.PointRepositoryImpl;
import kr.hhplus.be.server.infra.point.jpa.PointEntity;
import kr.hhplus.be.server.infra.point.jpa.PointJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("포인트 서비스 통합 테스트")
public class PointServiceIntegrationTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointJpaRepository pointJpaRepository;

    @BeforeEach
    void setup() {
        pointJpaRepository.deleteAll();
    }


    @Test
    @DisplayName("포인트_충전_후_잔액_확인")
    void chargePointSuccess() {
        // given
        Long userId = 1L;
        Long amount = 500L;
        Point point = Point.builder()
                .pointId(null)
                .userId(userId)
                .point(0L)
                .updatedAt(LocalDateTime.now())
                .build();
        pointRepository.save(point);

        // when
        Point savedPoint = pointService.charge(userId, amount);

        // then
        assertThat(savedPoint.point()).isEqualTo(500L);
    }
}
