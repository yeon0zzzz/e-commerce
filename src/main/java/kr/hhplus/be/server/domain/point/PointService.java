package kr.hhplus.be.server.domain.point;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PointRepository pointRepository;

    public Point charge(Long userId, Long amount) {

        Point point = pointRepository.findByUserId(userId);

        Point updateUserPoint = point.charge(amount);

        pointRepository.save(updateUserPoint);

        return updateUserPoint;
    }

    public Point use(Long userId, Long amount) {

        Point point = pointRepository.findByUserId(userId);

        Point updateUserPoint = point.use(amount);

        pointRepository.save(updateUserPoint);

        return updateUserPoint;
    }

    public Point getUserPoint(Long userId) {
        return pointRepository.findByUserId(userId);
    }
}
