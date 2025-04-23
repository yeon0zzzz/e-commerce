package kr.hhplus.be.server.infra.point.jpa;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.point.Point;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
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
        return PointEntity.builder()
                .pointId(point.pointId())
                .userId(point.userId())
                .point(point.point())
                .updatedAt(point.updatedAt())
                .build();
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
