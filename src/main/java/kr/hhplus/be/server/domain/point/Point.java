package kr.hhplus.be.server.domain.point;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record Point(
        long userId,
        long point,
        LocalDateTime updatedAt
) {
    private static final long MAX_POINT = 1_000_000L;

    public Point charge(long amount) {

        invalidAmount(amount);

        if ((this.point + amount) > MAX_POINT){
            throw new IllegalArgumentException(PointMessage.EXCEEDS_LIMIT);
        }

        return Point.builder()
                .userId(this.userId)
                .point(this.point + amount)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Point use(long amount) {

        invalidAmount(amount);

        if (this.point < amount){
            throw new IllegalArgumentException(PointMessage.INSUFFICIENT);
        }

        return Point.builder()
                .userId(this.userId)
                .point(this.point - amount)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void invalidAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(PointMessage.INVALID_AMOUNT);
        }
    }
}
