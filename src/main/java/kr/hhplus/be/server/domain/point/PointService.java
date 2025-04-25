package kr.hhplus.be.server.domain.point;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PointRepository pointRepository;

    @Transactional
    public Point charge(Long userId, Long amount) {

        Point point = pointRepository.findByUserId(userId);

        Point updateUserPoint = point.charge(amount);

        return pointRepository.save(updateUserPoint);
    }

    @Transactional
    public Point use(Long userId, Long amount) {

        Point point = pointRepository.findByUserId(userId);

        Point updateUserPoint = point.use(amount);

        return pointRepository.save(updateUserPoint);
    }

    @Transactional
    public Point findByUserId(Long userId) {
        return pointRepository.findByUserId(userId);
    }

    public Point save(Point point) {
        return pointRepository.save(point);
    }
}
