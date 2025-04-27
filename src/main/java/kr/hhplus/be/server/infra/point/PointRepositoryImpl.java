package kr.hhplus.be.server.infra.point;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointRepository;
import kr.hhplus.be.server.infra.point.jpa.PointJpaRepository;
import kr.hhplus.be.server.infra.point.jpa.PointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Point findByUserId(Long userId){
        return pointJpaRepository.findByUserId(userId)
                .map(PointEntity::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("포인트 없음"));
    }

    @Override
    public Point save(Point point){
        return PointEntity.toDomain(
                pointJpaRepository.save(
                        PointEntity.toEntity(point)
                )
        );
    }
}
