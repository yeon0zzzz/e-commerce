package kr.hhplus.be.server.domain.point;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record Point(
        Long pointId,
        Long userId,
        Long point,
        LocalDateTime updatedAt
) {
    private static final long MAX_POINT = 1_000_000L;

    public Point charge(Long amount) {

        invalidAmount(amount);

        if ((this.point + amount) > MAX_POINT){
            throw new IllegalArgumentException(PointMessage.EXCEEDS_LIMIT);
        }

        return Point.builder()
                .pointId(this.pointId)
                .userId(this.userId)
                .point(this.point + amount)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Point use(Long amount) {

        invalidAmount(amount);

        if (this.point < amount){
            throw new IllegalArgumentException(PointMessage.INSUFFICIENT);
        }

        return Point.builder()
                .pointId(this.pointId)
                .userId(this.userId)
                .point(this.point - amount)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void invalidAmount(Long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(PointMessage.INVALID_AMOUNT);
        }
    }
}
