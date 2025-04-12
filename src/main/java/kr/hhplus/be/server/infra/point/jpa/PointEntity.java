package kr.hhplus.be.server.infra.point.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import kr.hhplus.be.server.domain.point.Point;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "point")
    private long point;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static PointEntity toEntity(Point point) {
        PointEntity entity = new PointEntity();
        entity.userId = point.userId();
        entity.point = point.point();
        entity.updatedAt = point.updatedAt();
        return entity;
    }

}
