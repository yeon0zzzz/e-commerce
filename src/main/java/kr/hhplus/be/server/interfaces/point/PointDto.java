package kr.hhplus.be.server.interfaces.point;

import kr.hhplus.be.server.domain.point.Point;
import lombok.Builder;

import java.time.LocalDateTime;

public class PointDto {

    public record Request(
            Long amount
    ) {
    }

    @Builder
    public record Response(
            Long pointId,
            Long userId,
            Long point,
            LocalDateTime updatedAt
    ) {
        public static Response of(Point point) {
            return Response.builder()
                    .pointId(point.pointId())
                    .userId(point.userId())
                    .point(point.point())
                    .updatedAt(point.updatedAt())
                    .build();
        }
    }
}
