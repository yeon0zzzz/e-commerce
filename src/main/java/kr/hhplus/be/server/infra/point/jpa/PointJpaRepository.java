package kr.hhplus.be.server.infra.point.jpa;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PointEntity p where p.userId = :userId")
    Optional<PointEntity> findByUserId(Long userId);
}
