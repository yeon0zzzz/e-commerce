package kr.hhplus.be.server.infra.point.mapper;

import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.infra.point.jpa.PointEntity;


public class PointMapper {

    public static Point toDomain (PointEntity entity){
        return Point.builder()
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
