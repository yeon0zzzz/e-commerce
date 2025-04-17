package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.infra.point.PointRepositoryImpl;
import kr.hhplus.be.server.infra.point.jpa.PointEntity;
import kr.hhplus.be.server.infra.point.jpa.PointJpaRepository;
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
public class PointServiceIntegrationTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointRepository pointRepository;


    @Test
    @DisplayName("포인트_충전_후_잔액_확인")
    void chargePointSuccess() {
        // given
//        Long pointId = 1L;
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
        pointService.charge(userId, amount);

        // then
        Point result = pointRepository.findByUserId(userId);
        assertThat(result.point()).isEqualTo(500L);
    }
}
