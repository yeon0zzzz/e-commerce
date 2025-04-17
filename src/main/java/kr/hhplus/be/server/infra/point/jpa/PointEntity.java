package kr.hhplus.be.server.infra.point.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.point.Point;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "point")
    private Long point;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static PointEntity toEntity(Point point) {
        PointEntity entity = new PointEntity();
        entity.pointId = point.pointId();
        entity.userId = point.userId();
        entity.point = point.point();
        entity.updatedAt = point.updatedAt();
        return entity;
    }

    public static Point toDomain (PointEntity entity){
        return Point.builder()
                .pointId(entity.getPointId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
